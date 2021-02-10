package top.jayu.oa.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.jayu.oa.entity.OaProcess;
import top.jayu.oa.entity.OaProcessStep;
import top.jayu.oa.mapper.OaProcessMapper;

import java.util.ArrayList;
import java.util.List;

@Service
public class OaProcessStepService {

    @Autowired
    OaProcessMapper oaProcessMapper;

    public List<List<OaProcessStep>> getProcess(String type){
        OaProcess process = new OaProcess();
        process.setBillType(type);
        List<OaProcess> list = oaProcessMapper.list(process);
        List<List<OaProcessStep>> result = new ArrayList<>();
        return result;
    }

    private void generate(List<List<OaProcessStep>> result, List<OaProcess> list){
        for (int i=0; i<list.size(); i++){
            OaProcess process = list.get(i);
            Integer processConditionId = process.getProcessConditionId();
        }
    }

}
