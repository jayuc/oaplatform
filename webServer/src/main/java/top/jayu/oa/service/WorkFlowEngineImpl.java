package top.jayu.oa.service;

import cn.hutool.core.util.StrUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.jayu.oa.entity.OaBill;
import top.jayu.oa.entity.OaProcess;
import top.jayu.oa.entity.OaProcessCondition;
import top.jayu.oa.iter.SpringInvokeMethod;
import top.jayu.oa.iter.WorkFlowEngine;
import top.jayu.oa.util.ResultUtil;

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
    SpringInvokeMethod springInvokeMethod;

    @Override
    public Map<String, Object> deliver(OaBill bill) {
        ResultUtil.Result result = ResultUtil.build();

        // 第一步：获取流程步骤
        OaProcess process = getProcess(bill);

        // 第二步：获取步骤条件
        OaProcessCondition condition = getCondition(process.getProcessConditionId());
        // 步骤名称
        String candidateStep;
        int approveFunctionId;
        if(condition == null){  // 如果条件为空则直接进入下一步骤
            String step = process.getNextStep();
            if(StrUtil.isBlank(step)){
                throw new RuntimeException("找不到投递需要的流程节点");
            }
            candidateStep = step;
            approveFunctionId = process.getNextApproveFunctionId();
        }else {

        }

        return result.getResult();
    }

    // 第一步：获取流程步骤
    private OaProcess getProcess(OaBill bill){
        return oaProcessService.getStepByName(bill.getCurrentStep());
    }

    // 第二部：获取步骤条件
    private OaProcessCondition getCondition(Integer conditionId){
        if(conditionId == null){   // 如果没有条件则直接返回空
            return null;
        }
        return oaProcessConditionService.getConditionById(conditionId);
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

}
