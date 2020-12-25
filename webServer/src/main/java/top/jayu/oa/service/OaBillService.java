package top.jayu.oa.service;

import org.springframework.stereotype.Service;
import top.jayu.oa.entity.OaBill;

@Service
public class OaBillService {

    // 查询流程所属层级
    public Integer computeOrgLevel(OaBill bill){
        String applyOrgCodePriv = bill.getApplyOrgCodePriv();
        int length = applyOrgCodePriv.length();
        if(length == 6){
            if("340000".equals(applyOrgCodePriv)){
                Integer applyId = bill.getApplyId();
                if(applyId == 2){
                    return 1;
                }
                return 2;
            }else {
                return 3;
            }
        }else {

        }
        return 0;
    }

}
