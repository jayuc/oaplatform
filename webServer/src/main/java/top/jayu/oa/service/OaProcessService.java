package top.jayu.oa.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.jayu.oa.entity.OaProcess;
import top.jayu.oa.mapper.OaProcessMapper;

@Service
public class OaProcessService {

    @Autowired
    OaProcessMapper oaProcessMapper;

    OaProcess getStepByName(String name){
        return oaProcessMapper.getStepByName(name);
    }

}
