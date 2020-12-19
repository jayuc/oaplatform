package top.jayu.oa.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.jayu.oa.entity.OaProcessFunction;
import top.jayu.oa.mapper.OaProcessFunctionMapper;

@Service
public class OaProcessFunctionService {

    @Autowired
    OaProcessFunctionMapper oaProcessFunctionMapper;

    public OaProcessFunction getById(int id){
        return oaProcessFunctionMapper.getById(id);
    }

}
