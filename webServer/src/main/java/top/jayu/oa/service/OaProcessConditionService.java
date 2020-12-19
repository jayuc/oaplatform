package top.jayu.oa.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.jayu.oa.entity.OaProcessCondition;
import top.jayu.oa.mapper.OaProcessConditionMapper;

@Service
public class OaProcessConditionService {

    @Autowired
    OaProcessConditionMapper oaProcessConditionMapper;

    public OaProcessCondition getConditionById(int id){
        return oaProcessConditionMapper.getConditionById(id);
    }

}
