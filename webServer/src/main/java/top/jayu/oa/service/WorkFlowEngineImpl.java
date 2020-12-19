package top.jayu.oa.service;

import cn.hutool.core.util.StrUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.jayu.oa.entity.*;
import top.jayu.oa.iter.SpringInvokeMethod;
import top.jayu.oa.iter.WorkFlowEngine;
import top.jayu.oa.mapper.OaBillMapper;
import top.jayu.oa.mapper.OaBillOperaMapper;
import top.jayu.oa.util.ResultUtil;

import java.util.Date;
import java.util.Map;

/**
 * Created by 余杰 on 2020/12/18 11:37
 */

@Service
public class WorkFlowEngineImpl implements WorkFlowEngine {

    @Autowired
    OaProcessService oaProcessService;
    @Autowired
    OaProcessConditionService oaProcessConditionService;
    @Autowired
    OaProcessFunctionService oaProcessFunctionService;
    @Autowired
    SpringInvokeMethod springInvokeMethod;
    @Autowired
    OaBillMapper oaBillMapper;
    @Autowired
    OaBillOperaMapper oaBillOperaMapper;

    @Override
    public Map<String, Object> deliver(OaBill bill) {
        ResultUtil.Result result = ResultUtil.build();

        String prevStep = bill.getCurrentStep();

        // 第一步：获取流程步骤
        OaProcess process = getProcess(bill);

        // 第二步：获取步骤条件
        OaProcessCondition condition = getCondition(process.getProcessConditionId());
        // 步骤名称
        String candidateStep = null;
        int approveFunctionId = 0;
        if(condition == null){  // 如果条件为空则直接进入下一步骤
            String step = process.getNextStep();
            if(StrUtil.isBlank(step)){
                throw new RuntimeException("找不到投递需要的流程节点");
            }
            candidateStep = step;
            approveFunctionId = process.getNextApproveFunctionId();
        }else {
            StepTemp stepTemp = processCondition(condition.getProcessConditionId(), bill);
            if(stepTemp != null){
                candidateStep = stepTemp.step;
                approveFunctionId = stepTemp.approveFunctionId;
            }
        }

        // 第三步：查询下一步审批人
        if(!"end".equals(candidateStep)){
            ApproveResult approveResult = findApprove(approveFunctionId, bill);
            if(!StrUtil.isBlank(approveResult.approveList)){   // 找到了审批人
                bill.setNextApproveList(approveResult.approveList);
                result.property("approveIdList", approveResult.approveList);
            }else {
                result.error(approveResult.stepName + "未配置，请联系管理员进行配置");
            }
        }else {
            bill.setStopFlag((byte) 1);
            result.property("info", "流程已经完成了");
        }

        if(candidateStep != null){
            bill.setCurrentStep(bill.getCurrentStep() + candidateStep);
        }else {
            throw new RuntimeException("找不到投递需要的流程节点");
        }

        // 第四步：订单更新到数据库
        processToDb(bill);
        // 第五步：记录日志
        processToLog(bill, prevStep);

        return result.getResult();
    }

    // 第一步：获取流程步骤
    private OaProcess getProcess(OaBill bill){
        return oaProcessService.getProcess(bill.getBillType(), bill.getCurrentStep());
    }

    // 第二步：获取步骤条件
    private OaProcessCondition getCondition(Integer conditionId){
        if(conditionId == null){   // 如果没有条件则直接返回空
            return null;
        }
        return oaProcessConditionService.getConditionById(conditionId);
    }

    // 第三步：查询审批人
    private ApproveResult findApprove(int approveFunctionId, OaBill bill){
        OaProcessFunction processFunction = oaProcessFunctionService.getById(approveFunctionId);
        String serviceName = processFunction.getIocEntityName();
        String methodName = processFunction.getIocEntityMethod();
        String propertyName = processFunction.getInput();
        ApproveResult approveResult = new ApproveResult();
        approveResult.stepName = processFunction.getFunctionName();
        try {
            Object obj = springInvokeMethod.invokeProperty(bill, propertyName);
            approveResult.approveList = (String) springInvokeMethod.invokeMethod(serviceName, methodName, new Object[]{obj});
            return approveResult;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return approveResult;
    }

    // 第四步：更新到数据库
    private int processToDb(OaBill bill){
        Integer billId = bill.getBillId();
        if(billId == null){
            bill.setApplyId(bill.getUserId());
            return oaBillMapper.insert(bill);
        }else {
            return oaBillMapper.approve(bill);
        }
    }

    // 第五步：记录日志
    private int processToLog(OaBill bill, String step){
        OaBillOpera oaBillOpera = new OaBillOpera();
        oaBillOpera.setBillCode(bill.getBillCode());
        oaBillOpera.setBillType(bill.getBillType());
        oaBillOpera.setBillStep(step);
        oaBillOpera.setContent(bill.getContent());
        oaBillOpera.setOperaId(bill.getUserId());
        if(bill.getPassFlag() == null){
            bill.setPassFlag((byte) 0);
        }
        oaBillOpera.setPassFlag(bill.getPassFlag());
        return oaBillOperaMapper.insert(oaBillOpera);
    }

    private StepTemp processCondition(Integer conditionId, OaBill bill){

        OaProcessCondition condition = getCondition(conditionId);
        String serviceName = condition.getIocEntityName();
        String methodName = condition.getIocEntityMethod();
        String propertyName = condition.getInput();
        Boolean ifTrue = null;
        try {
            Object obj = springInvokeMethod.invokeProperty(bill, propertyName);
            ifTrue = (Boolean) springInvokeMethod.invokeMethod(serviceName, methodName, new Object[]{obj});
        } catch (Exception e) {
            e.printStackTrace();
        }
        if(ifTrue != null){
            if(ifTrue){
                Integer successConditionId = condition.getSuccessConditionId();
                if(successConditionId != null){
                    return processCondition(successConditionId, bill);
                }
                StepTemp stepTemp = new StepTemp();
                stepTemp.step = condition.getSuccessTo();
                stepTemp.approveFunctionId = condition.getSuccessApproveFunctionId();
                return stepTemp;
            }else {
                Integer failConditionId = condition.getFailConditionId();
                if(failConditionId != null){
                    return processCondition(failConditionId, bill);
                }
                StepTemp stepTemp = new StepTemp();
                stepTemp.step = condition.getFailTo();
                stepTemp.approveFunctionId = condition.getFailApproveFunctionId();
                return stepTemp;
            }
        }
        return null;
    }

    private class StepTemp{
        String step;
        int approveFunctionId;
    }

    private class ApproveResult{
        String approveList;
        String stepName;
    }

}
