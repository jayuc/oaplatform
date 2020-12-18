package top.jayu.oa.service;

import org.springframework.stereotype.Service;
import top.jayu.oa.entity.OaBill;
import top.jayu.oa.iter.WorkFlowEngine;

import java.util.Map;

/**
 * Created by 余杰 on 2020/12/18 11:37
 */

@Service
public class WorkFlowEngineImpl implements WorkFlowEngine {

    @Override
    public Map<String, Object> deliver(OaBill bill) {
        return null;
    }

}
