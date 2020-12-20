package top.jayu.oa.iter;

import top.jayu.oa.entity.OaBill;

import java.util.Map;

/**
 * 工作流引擎
 * Created by 余杰 on 2020/12/18 10:37
 */

public interface WorkFlowEngine {

    // 投递
    // autoDb： 结果是否入库，当模拟操作（查询进度）的时候不需要入库
    Map<String, Object> deliver(OaBill bill, boolean autoDb);

}
