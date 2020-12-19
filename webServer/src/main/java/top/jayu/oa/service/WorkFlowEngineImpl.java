package top.jayu.oa.service;

import cn.hutool.core.util.StrUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.jayu.oa.entity.OaBill;
import top.jayu.oa.entity.OaProcess;
import top.jayu.oa.entity.OaProcessCondition;
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

    @Override
    public Map<String, Object> deliver(OaBill bill) {
        ResultUtil.Result result = ResultUtil.build();

        // 第一步：获取流程步骤
        OaProcess process = getProcess(bill);

        // 第二步：获取步骤条件
        OaProcessCondition condition = getCondition(process);
        if(condition == null){  // 如果条件为空则直接进入下一步骤
            String step = process.getNextStep();
            if(StrUtil.isBlank(step)){
                throw new RuntimeException("找不到投递需要的流程节点");
            }
            bill.setCurrentStep(step);
            result.property("currentStep", step);
        }else {

        }

        return result.getResult();
    }

    // 第一步：获取流程步骤
    private OaProcess getProcess(OaBill bill){
        return oaProcessService.getStepByName(bill.getCurrentStep());
    }

    // 第二部：获取步骤条件
    private OaProcessCondition getCondition(OaProcess process){
        Integer id = process.getProcessId();
        if(id == null){   // 如果没有条件则直接返回空
            return null;
        }
        return oaProcessConditionService.getConditionById(id);
    }

}
