package top.jayu.oa.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.jayu.oa.entity.OaProcessStep;
import top.jayu.oa.entity.ProcessStep;
import top.jayu.oa.mapper.OaProcessMapper;
import top.jayu.oa.mapper.OaProcessStepMapper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class OaProcessStepService {

    @Autowired
    OaProcessMapper oaProcessMapper;
    @Autowired
    OaProcessStepMapper oaProcessStepMapper;

    Map<Integer, OaProcessStep> conditionMap = new HashMap<>();
    Map<Integer, OaProcessStep> functionMap = new HashMap<>();

    OaProcessStep startStep = new OaProcessStep();
    OaProcessStep endStep = null;

    void processMap(){
        List<OaProcessStep> list = oaProcessStepMapper.list(null);
        list.forEach((step) -> {
            if("1".equals(step.getStepType())){
                functionMap.put(step.getStepValue(), step);
            }else if("2".equals(step.getStepType())){
                conditionMap.put(step.getStepValue(), step);
            }else if("3".equals(step.getStepType())){
                endStep = step;
            }
        });

        startStep.setStepName("工单申请");
    }

    public List<List<OaProcessStep>> getProcess(String type){
        if(functionMap.isEmpty()){
            processMap();
        }
        List<ProcessStep> list = oaProcessMapper.stepProcess(type);
        List<List<OaProcessStep>> result = new ArrayList<>();
        generate(result, list);
        return result;
    }

    private void generate(List<List<OaProcessStep>> result, List<ProcessStep> list){
        int minLevel = 3;
        for (int i=0; i<list.size(); i++){
            ProcessStep process = list.get(i);
            if("root".equals(process.getParentStep())){
                List<OaProcessStep> steps = new ArrayList<>();
                steps.add(startStep);
                result.add(steps);
                if(process.getProcessConditionId() != null){
                    minLevel = 5;
                }
            }else if(process.getOrgPrivLen() <= minLevel){
                result.add(getThisStep(list.get(i-1)));
                Integer processConditionId = process.getProcessConditionId();
                if(processConditionId != null){
                    List<OaProcessStep> s2 = new ArrayList<>();
                    s2.add(conditionMap.get(processConditionId));
                    if("end".equals(process.getFailTo())){
                        s2.add(endStep);
                    }else {
                        s2.add(functionMap.get(process.getFailApproveFunctionId()));
                    }
                    result.add(s2);
                }
                if("end".equals(process.getNextStep())){
                    List<OaProcessStep> steps = new ArrayList<>();
                    steps.add(endStep);
                    result.add(steps);
                }
            }
        }
    }

    private List<OaProcessStep> getThisStep(ProcessStep step){
        Integer nextApproveFunctionId = step.getNextApproveFunctionId();
        List<OaProcessStep> steps = new ArrayList<>();
        if(nextApproveFunctionId != null){
            steps.add(functionMap.get(nextApproveFunctionId));
            return steps;
        }
        if("end".equals(step.getSuccessTo())){
            steps.add(endStep);
            return steps;
        }
        steps.add(functionMap.get(step.getSuccessApproveFunctionId()));
        return steps;
    }

}
