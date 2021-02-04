package top.jayu.oa.service;

import cn.hutool.core.util.StrUtil;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.jayu.oa.entity.*;
import top.jayu.oa.iter.SpringInvokeMethod;
import top.jayu.oa.iter.WorkFlowEngine;
import top.jayu.oa.mapper.CodeMapper;
import top.jayu.oa.mapper.OaBillMapper;
import top.jayu.oa.mapper.OaBillOperaMapper;
import top.jayu.oa.util.ResultUtil;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
    UserService userService;
    @Autowired
    SpringInvokeMethod springInvokeMethod;
    @Autowired
    OaBillMapper oaBillMapper;
    @Autowired
    OaBillOperaMapper oaBillOperaMapper;
    @Autowired
    CodeMapper codeMapper;

    static Map<String, Code> BILL_TYPE_MAP = null;

    private void initBillTypeMap(){
        if(BILL_TYPE_MAP == null){
            Code dto = new Code();
            dto.setCode((short) 1);
            List<Code> list = codeMapper.list(dto);
            BILL_TYPE_MAP = list.stream().collect(Collectors.toMap(Code::getCodeNo, code -> code));
        }
    }

    @Override
    public Map<String, Object> deliver(OaBill bill, boolean autoDb) {

        initBillTypeMap();

        ResultUtil.Result result = ResultUtil.build();

        log.info("");
        log.info("进入流程 ===> " + bill);

        String prevStep = bill.getCurrentStep();
        OaProcess process = getProcess(bill);

        if(process == null){
            result.error("流程未配置，请联系管理员");
            return result.getResult();
        }

        if(!autoDb){  // 流程模拟时候找本步骤审批人
            log.info("simulateDeliver get next approve list ===> " + bill.getNextApproveList());
            if(StrUtil.isBlank(bill.getNextApproveList())){
                result.property("approveName", "未配置");
            }else {
                String approveName = userService.getApproveName(bill.getNextApproveList());
                log.info("simulateDeliver get approve name ===> " + approveName);
                result.property("approveName", approveName);
            }
        }else {
            result.property("approveName", bill.getCurrentUserName());
        }

        // 是否是新建工单，区别与驳回后处置工单
        boolean yesNewBill = true;

        // 进入流程
        Byte passFlag = bill.getPassFlag();
        if(passFlag == 2){  // 不同意

            bill.setCurrentStep("00");
            bill.setNextApproveList("");
            bill.setHistoryApproveList("");
            bill.setApproveId(bill.getCurrentUserId());
            bill.setApproveName(bill.getCurrentUserName());
            bill.setApproveContent(bill.getApproveOpinion());

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
                    result.property("nextApproveName", approveResult.approveNameList);
                }else {
                    bill.setNextApproveList("");
                    result.error(approveResult.stepName + "未配置，请联系管理员进行配置");
                    // 不入库
                    autoDb = false;
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
                result.property("info", "流程已经完成了");
                log.info("pass flag: 1, step3-2: end ===> 流程已经完成了");
            }

            String currentOrgCodePriv = bill.getCurrentOrgCodePriv();
            if(!StrUtil.isBlank(currentOrgCodePriv)){
                String upOrgCodePriv = currentOrgCodePriv.substring(0, currentOrgCodePriv.length() - 2);
                bill.setApproveOrgCodePriv(upOrgCodePriv);
            }

            bill.setApproveId(bill.getCurrentUserId());
            bill.setApproveName(bill.getCurrentUserName());
            bill.setApproveContent(bill.getApproveOpinion());

        }else if(passFlag == 0){  // 新建表单

            // 第一步：获取步骤条件
            OaProcessCondition condition = getCondition(process.getProcessConditionId());
            log.info("pass flag: 0, step1-1: process condition ===> " + condition);

            String preStep = "";
            if(condition != null){
                StepTemp stepTemp = processCondition(condition.getProcessConditionId(), bill);
                preStep = stepTemp.step;
                log.info("pass flag: 0, step1-2: stepTemp ===> " + stepTemp);
            }

            //  第二步: 查询流程层级
            OaBillService.Level level = oaBillService.computeOrgLevels(bill, process);
            float processLevel = level.level;
            log.info("pass flag: 0, step2: process level ===> " + level);

            bill.setApproveName(bill.getCurrentUserName());

            if(processLevel == 0){

                bill.setStopFlag((byte) 1);
                bill.setCurrentStep("end");
                result.property("info", "流程已经完成了");
                log.info("pass flag: 0, step3: end ===> 流程已经完成了");

            }else if(processLevel > 0){

                // 第三步：找到对应的流程
                String processName = level.currentStep;
                if(processName == null){
                    processName = findProcess(bill, processLevel, preStep);
                }
                log.info("pass flag: 0, step3: process name ===> " + processName);
                if(processName != null){
                    bill.setCurrentStep(processName);
                    bill.setStopFlag((byte) 2);
                    if(!StrUtil.isBlank(level.approveId)){
                        bill.setNextApproveList(level.approveId);
                        result.property("approveIdList", level.approveId);
                        result.property("nextApproveName", level.approveName);
                    }else {
                        result.error(level.stepName + "未配置，请联系管理员进行配置");
                        // 不入库
                        autoDb = false;
                    }
                }else {
                    result.error("找不到对应的流程");
                    // 不入库
                    autoDb = false;
                }

            }else {
                log.warn("pass flag: 0, step3: 找不到流程层级");
                result.error("找不到对应的流程");
                // 不入库
                autoDb = false;
            }

            if(bill.getApproveId() != null && bill.getApproveId() > 0){
                yesNewBill = false;   // 驳回处置工单
                bill.setApproveName("");
                bill.setApproveId(null);
                bill.setApproveContent("");
            }

        }

        if(autoDb){  // 入库

            // 入库前处理参数
            handleParamBeforeToDb(bill, process, yesNewBill);

            // 第四步：订单更新到数据库
            processToDb(bill, yesNewBill);

            // 第五步：记录日志
            processToLog(bill, prevStep, process.getProcessDesc(), process.getOrgPrivLen());

        }else {
            result.property("processDesc", process.getProcessDesc());
            result.property("bill", bill);
        }

        return result.getResult();
    }

    // 找到对应的流程
    private String findProcess(OaBill bill, float processLevel, String preStep){
        OaProcess process = new OaProcess();
        process.setBillType(bill.getBillType());
        List<OaProcess> list = oaProcessService.list(process);
        String pStep = bill.getCurrentStep() + preStep;
        if(list.size() > 0){
            for (OaProcess oaProcess:list){
                if(oaProcess.getOrgPrivLen() == processLevel
                        && oaProcess.getCurrentStep().startsWith(pStep)){
                    return oaProcess.getCurrentStep();
                }
            }
            for (int i=0; i<list.size()-1; i++){
                if(processLevel > list.get(i).getOrgPrivLen() &&
                        processLevel < list.get(i+1).getOrgPrivLen()){
                    return list.get(i+1).getCurrentStep();
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
            OaProcessFunctionResult oaProcessFunctionResult = (OaProcessFunctionResult) springInvokeMethod.invokeMethod(serviceName, methodName, new Object[]{obj});
            approveResult.approveList = oaProcessFunctionResult.getApproveList();
            approveResult.approveNameList = oaProcessFunctionResult.getApproveNameList();
            log.info("step3-1: find approve result (" + functionName + ") ===> " + approveResult.approveList +
              ", approve name ===>" + approveResult.approveNameList);
            return approveResult;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return approveResult;
    }

    // 入库前处理参数
    private void handleParamBeforeToDb(OaBill bill, OaProcess process, boolean yesNewBill){
        // 入库前处理流程节点流转历史
        String processNodeHistory = bill.getHistoryProcessList();
        if(StrUtil.isBlank(processNodeHistory)){
            processNodeHistory = process.getProcessDesc() + "@" + bill.getApproveName();
        }else {
            processNodeHistory = processNodeHistory + "," + process.getProcessDesc() + "@" + bill.getApproveName();
        }
        if(!yesNewBill){  // 工单驳回后处置
            processNodeHistory = process.getProcessDesc();
        }
        bill.setHistoryProcessList(processNodeHistory);
    }

    // 第四步：更新到数据库
    private int processToDb(OaBill bill, boolean yesNewBill){
        Integer billId = bill.getBillId();
        if(billId == null){
            log.info("step4: bill insert ===> " + bill);
            return oaBillMapper.insert(bill);
        }else {
            // 处理历史审批人
            String approveList = bill.getHistoryApproveList();
            if(StrUtil.isBlank(approveList)){
                approveList = "," + bill.getCurrentUserId() + ",";
            }else {
                approveList = approveList + "," + bill.getCurrentUserId() + ",";
            }
            bill.setHistoryApproveList(approveList);
            if(bill.getStopFlag() == 1){  // 流程结束
                bill.setNextApproveList("");
            }
            log.info("step4: bill update ===> " + bill);
            log.info("step4: bill yes new ===> " + yesNewBill);
            if(yesNewBill){
                return oaBillMapper.approve(bill);
            }else {
                return oaBillMapper.update(bill);
            }
        }
    }

    // 第五步：记录日志
    private int processToLog(OaBill bill, String step, String stepName, Float stepLeve){
        OaBillOpera oaBillOpera = new OaBillOpera();
        oaBillOpera.setBillId(bill.getBillId());
        oaBillOpera.setBillCode(bill.getBillCode());
        oaBillOpera.setBillType(bill.getBillType());
        oaBillOpera.setBillStep(step);
        oaBillOpera.setBillStepName(stepName);
        oaBillOpera.setContent(bill.getApproveOpinion());
        oaBillOpera.setOperaId(bill.getCurrentUserId());
        oaBillOpera.setOperaName(bill.getCurrentUserName());
        oaBillOpera.setOperaOrgId(bill.getCurrentOrgId());
        if(bill.getPassFlag() == null){
            bill.setPassFlag((byte) 0);
        }
        Code code = BILL_TYPE_MAP.get(bill.getBillType());
        if(code != null){
            oaBillOpera.setBillTypeName(code.getName());
        }
        oaBillOpera.setStepOrgLevel(stepLeve);
        oaBillOpera.setPassFlag(bill.getPassFlag());
        if(StrUtil.isBlank(oaBillOpera.getContent())){
            if(bill.getPassFlag() == 1){
                oaBillOpera.setContent("同意");
            }else if(bill.getPassFlag() == 2){
                oaBillOpera.setContent("不同意");
            }
        }
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
            log.info("do method: processCondition : execute condition (" + conditionDesc + ") ===> serviceName: " + serviceName + " methodName: "
                    + methodName + " param: " + obj);
            ifTrue = (Boolean) springInvokeMethod.invokeMethod(serviceName, methodName, new Object[]{obj});
            log.info("do method: processCondition: execute condition result (" + conditionDesc + ") ===> " + ifTrue);
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
                    Integer successApproveFunctionId = condition.getSuccessApproveFunctionId();
                    if(successApproveFunctionId != null && successApproveFunctionId > 0){
                        stepTemp.approveFunctionId = condition.getSuccessApproveFunctionId();
                    }
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
                    Integer failApproveFunctionId = condition.getFailApproveFunctionId();
                    if(failApproveFunctionId != null && failApproveFunctionId > 0){
                        stepTemp.approveFunctionId = failApproveFunctionId;
                    }
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
        String approveNameList;
        String stepName;
    }

}
