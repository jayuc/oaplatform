package top.jayu.oa.service;

import cn.hutool.core.util.StrUtil;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.jayu.oa.entity.*;
import top.jayu.oa.iter.SpringInvokeMethod;
import top.jayu.oa.iter.WorkFlowEngine;
import top.jayu.oa.mapper.OaBillMapper;
import top.jayu.oa.mapper.OaBillOperaMapper;
import top.jayu.oa.util.ResultUtil;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by 余杰 on 2020/12/18 11:37
 */

@Service
@Slf4j
public class WorkFlowEngineImpl implements WorkFlowEngine {

    @Autowired
    OaProcessService oaProcessService;
    @Autowired
    OaProcessConditionService oaProcessConditionService;
    @Autowired
    OaProcessFunctionService oaProcessFunctionService;
    @Autowired
    OaBillService oaBillService;
    @Autowired
    SpringInvokeMethod springInvokeMethod;
    @Autowired
    OaBillMapper oaBillMapper;
    @Autowired
    OaBillOperaMapper oaBillOperaMapper;

    @Override
    public Map<String, Object> deliver(OaBill bill, boolean autoDb) {
        ResultUtil.Result result = ResultUtil.build();

        log.info("");
        log.info("进入流程 ===> " + bill);

        String prevStep = bill.getCurrentStep();
        OaProcess process = getProcess(bill);

        // 进入流程
        Byte passFlag = bill.getPassFlag();
        if(passFlag == 2){  // 不同意

            bill.setApproveOrgCodePriv(bill.getCurrentOrgCodePriv());

        }else if(passFlag == 1){  // 同意

            // 第一步：获取流程步骤
            log.info("pass flag: 1, step1: process ===> " + process);

            // 第二步：获取步骤条件
            OaProcessCondition condition = getCondition(process.getProcessConditionId());
            log.info("pass flag: 1, step2: condition ===> " + condition);
            // 步骤名称
            String candidateStep = null;
            int approveFunctionId = 0;
            if(condition == null){  // 如果条件为空则直接进入下一步骤
                String step = process.getNextStep();
                log.info("pass flag: 1, step2-1: step ===> " + step);
                if(StrUtil.isBlank(step)){
                    throw new RuntimeException("找不到投递需要的流程节点");
                }
                candidateStep = step;
                if(!"end".equals(step)){
                    approveFunctionId = process.getNextApproveFunctionId();
                }
            }else {
                StepTemp stepTemp = processCondition(condition.getProcessConditionId(), bill);
                log.info("pass flag: 1, step2-2: stepTemp ===> " + stepTemp);
                if(stepTemp != null){
                    candidateStep = stepTemp.step;
                    if(!"end".equals(candidateStep)){
                        approveFunctionId = stepTemp.approveFunctionId;
                    }
                }
            }

            log.info("pass flag: 1, step3: candidateStep ===> " + candidateStep);
            // 第三步：查询下一步审批人
            if(!"end".equals(candidateStep)){

                ApproveResult approveResult = findApprove(approveFunctionId, bill);
                log.info("pass flag: 1, step3-1: approveResult ===> " + approveResult);
                if(!StrUtil.isBlank(approveResult.approveList) && !",0,".equals(approveResult.approveList)){   // 找到了审批人
                    bill.setNextApproveList(approveResult.approveList);
                    result.property("approveIdList", approveResult.approveList);
                }else {
                    result.error(approveResult.stepName + "未配置，请联系管理员进行配置");
                }

                if(candidateStep != null){
                    bill.setCurrentStep(bill.getCurrentStep() + candidateStep);
                }else {
                    throw new RuntimeException("找不到投递需要的流程节点");
                }

                bill.setStopFlag((byte) 2);


            }else {
                bill.setStopFlag((byte) 1);
                bill.setCurrentStep("end");
                bill.setNextApproveList("");
                result.property("info", "流程已经完成了");
                log.info("pass flag: 1, step3-2: end ===> 流程已经完成了");
            }

            String currentOrgCodePriv = bill.getCurrentOrgCodePriv();
            if(!StrUtil.isBlank(currentOrgCodePriv)){
                String upOrgCodePriv = currentOrgCodePriv.substring(0, currentOrgCodePriv.length() - 2);
                bill.setApproveOrgCodePriv(upOrgCodePriv);
            }

        }else if(passFlag == 0){  // 新建表单

            //  第一步: 查询流程层级
            OaBillService.Level level = oaBillService.computeOrgLevels(bill);
            int processLevel = level.level;
            log.info("pass flag: 0, step1: process level ===> " + level);

            if(processLevel == 0){

                bill.setStopFlag((byte) 1);
                bill.setCurrentStep("end");
                result.property("info", "流程已经完成了");
                log.info("pass flag: 0, step1: end ===> 流程已经完成了");

            }else if(processLevel > 0){

                // 第二步：找到对应的流程
                String processName = findProcess(bill, processLevel);
                log.info("pass flag: 0, step2: process name ===> " + processName);
                if(processName != null){
                    bill.setCurrentStep(processName);
                    bill.setStopFlag((byte) 2);
                    bill.setNextApproveList("," + level.approveId + ",");
                }else {
                    result.error("找不到对应的流程");
                    return result.getResult();
                }

            }else {
                log.warn("pass flag: 0, step1: 找不到流程层级");
                result.error("找不到对应的流程");
                return result.getResult();
            }

        }

        if(autoDb){  // 入库

            // 入库前处理参数
            handleParamBeforeToDb(bill, process);

            // 第四步：订单更新到数据库
            processToDb(bill);

            // 第五步：记录日志
            processToLog(bill, prevStep);

        }else {
            result.property("processDesc", process.getProcessDesc());
            result.property("bill", bill);
        }

        return result.getResult();
    }

    // 找到对应的流程
    private String findProcess(OaBill bill, int processLevel){
        OaProcess process = new OaProcess();
        process.setBillType(bill.getBillType());
        List<OaProcess> list = oaProcessService.list(process);
        if(list.size() > 0){
            for (OaProcess oaProcess:list){
                if(oaProcess.getOrgPrivLen() == processLevel){
                    return oaProcess.getCurrentStep();
                }
            }
        }
        return null;
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
        String functionName = processFunction.getFunctionName();
        ApproveResult approveResult = new ApproveResult();
        approveResult.stepName = processFunction.getFunctionName();
        try {
            Object obj = bill;
            if(!"OaBill".equals(propertyName)){
                obj = springInvokeMethod.invokeProperty(bill, propertyName);
            }
            log.info("step3-1: find approve (" + functionName + ") ===> serviceName: " + serviceName + " methodName: "
                    + methodName + " param: " + obj);
            approveResult.approveList = (String) springInvokeMethod.invokeMethod(serviceName, methodName, new Object[]{obj});
            log.info("step3-1: find approve result (" + functionName + ") ===> " + approveResult.approveList);
            return approveResult;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return approveResult;
    }

    // 入库前处理参数
    private void handleParamBeforeToDb(OaBill bill, OaProcess process){
        // 入库前处理流程节点流转历史
        String processNodeHistory = bill.getHistoryProcessList();
        if(StrUtil.isBlank(processNodeHistory)){
            processNodeHistory = process.getProcessDesc();
        }else {
            processNodeHistory = processNodeHistory + "," + process.getProcessDesc();
        }
        bill.setHistoryProcessList(processNodeHistory);
    }

    // 第四步：更新到数据库
    private int processToDb(OaBill bill){
        Integer billId = bill.getBillId();
        if(billId == null){
            log.info("step4: bill insert ===> " + bill);
            return oaBillMapper.insert(bill);
        }else {
            // 处理历史审批人
            String approveList = bill.getHistoryApproveList();
            if(StrUtil.isBlank(approveList)){
                approveList = bill.getNextApproveList();
            }else {
                approveList = approveList + "," + bill.getNextApproveList();
            }
            bill.setHistoryApproveList(approveList);
            log.info("step4: bill update ===> " + bill);
            return oaBillMapper.approve(bill);
        }
    }

    // 第五步：记录日志
    private int processToLog(OaBill bill, String step){
        OaBillOpera oaBillOpera = new OaBillOpera();
        oaBillOpera.setBillCode(bill.getBillCode());
        oaBillOpera.setBillType(bill.getBillType());
        oaBillOpera.setBillStep(step);
        oaBillOpera.setContent(bill.getApproveOpinion());
        oaBillOpera.setOperaId(bill.getCurrentUserId());
        if(bill.getPassFlag() == null){
            bill.setPassFlag((byte) 0);
        }
        oaBillOpera.setPassFlag(bill.getPassFlag());
        log.info("step5: oaBillOpera insert ===> " + oaBillOpera);
        return oaBillOperaMapper.insert(oaBillOpera);
    }

    private StepTemp processCondition(Integer conditionId, OaBill bill){

        OaProcessCondition condition = getCondition(conditionId);
        String serviceName = condition.getIocEntityName();
        String methodName = condition.getIocEntityMethod();
        String propertyName = condition.getInput();
        String conditionDesc = condition.getConditionDesc();
        Boolean ifTrue = null;
        try {
            Object obj = bill;
            if(!"OaBill".equals(propertyName)){
                obj = springInvokeMethod.invokeProperty(bill, propertyName);
            }
            log.info("step2-2: execute condition (" + conditionDesc + ") ===> serviceName: " + serviceName + " methodName: "
                    + methodName + " param: " + obj);
            ifTrue = (Boolean) springInvokeMethod.invokeMethod(serviceName, methodName, new Object[]{obj});
            log.info("step2-2: execute condition result (" + conditionDesc + ") ===> " + ifTrue);
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
                if(!"end".equals(stepTemp.step)){
                    stepTemp.approveFunctionId = condition.getSuccessApproveFunctionId();
                }
                return stepTemp;
            }else {
                Integer failConditionId = condition.getFailConditionId();
                if(failConditionId != null){
                    return processCondition(failConditionId, bill);
                }
                StepTemp stepTemp = new StepTemp();
                stepTemp.step = condition.getFailTo();
                if(!"end".equals(stepTemp.step)){
                    stepTemp.approveFunctionId = condition.getFailApproveFunctionId();
                }
                return stepTemp;
            }
        }
        return null;
    }

    @ToString
    private class StepTemp{
        String step;
        int approveFunctionId;
    }

    @ToString
    private class ApproveResult{
        String approveList;
        String stepName;
    }

}
