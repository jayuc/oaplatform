package top.jayu.oa.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.jayu.oa.entity.OaProcess;
import top.jayu.oa.mapper.OaProcessMapper;

import java.util.List;

@Service
public class OaProcessService {

    @Autowired
    OaProcessMapper oaProcessMapper;

    public OaProcess getProcess(Byte billType, String stepName){
        return oaProcessMapper.getProcess(billType, stepName);
    }

    public List<OaProcess> list(OaProcess process){
        return oaProcessMapper.list(process);
    }

}
