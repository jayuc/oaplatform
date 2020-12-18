package top.jayu.oa.iter;

import top.jayu.oa.entity.OaBill;

import java.util.Map;

/**
 * 工作流引擎
 * Created by 余杰 on 2020/12/18 10:37
 */

public interface WorkFlowEngine {

    // 投递
    Map<String, Object> deliver(OaBill bill);

}
