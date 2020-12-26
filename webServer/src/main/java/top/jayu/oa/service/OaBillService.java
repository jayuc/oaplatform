package top.jayu.oa.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.jayu.oa.entity.OaBill;
import top.jayu.oa.entity.OaProcess;
import top.jayu.oa.entity.Org;
import top.jayu.oa.mapper.OrgMapper;

@Service
public class OaBillService {

    @Autowired
    OrgMapper orgMapper;
    @Autowired
    OaProcessService oaProcessService;
    @Autowired
    OrgService orgService;

    // 查找上级部门负责人
    public String findUpOrgLeader(OaBill bill){
        String approveOrgCodePriv = bill.getApproveOrgCodePriv();
        if(approveOrgCodePriv == null){  // 没有审批过的
            return orgService.findOrgLeader(bill.getApplyOrgId());
        }
        String upOrgCodePriv = approveOrgCodePriv.substring(0, approveOrgCodePriv.length() - 2);
        Integer upOrgLeader = orgMapper.findOrgLeaderByPriv(upOrgCodePriv);
        return "," + upOrgLeader + ",";
    }

    // 判断是否循环查找部门负责人
    public boolean loopOrgLeader(OaBill bill){
        String currentStep = bill.getCurrentStep();
        if(currentStep.startsWith("loopOrgLeader")){  // 机构负责人循环步骤
            OaProcess process = getProcess(bill.getBillType(), bill.getCurrentStep());
            String nextStep = process.getNextStep();
            OaProcess nextProcess = getProcess(bill.getBillType(), nextStep);
            Byte nextOrgPrivLen = nextProcess.getOrgPrivLen();
            int level = computeOrgLevel(bill);
            if(nextOrgPrivLen == level){
                return true;
            }
            return false;
        }
        return false;
    }

    // 查询流程所属层级
    public Integer computeOrgLevel(OaBill bill){
        String applyOrgCodePriv = bill.getApplyOrgCodePriv();
        int length = applyOrgCodePriv.length();
        int applyId = bill.getApplyId();
        // 申请人机构
        Org org = orgMapper.getById(bill.getApplyOrgId());
        if(length == 6){
            if("340000".equals(applyOrgCodePriv)){
                if(applyId == 2){  // 市局负责人
                    return 0;
                }
                return 1;
            }
            return generateLevel(3, applyId, org);
        }else {
            if(length == 8){
                if(org.getYesLeader() != null && org.getYesLeader() == 1){   // 领导机构
                    return generateLevel(3, applyId, org);
                }
                return generateLevel(5, applyId, org);
            }else if(length > 8){
                int level = 5 + (length - 8)/2;
                return generateLevel(level, applyId, org);
            }
        }
        return -1;
    }

    private Integer generateLevel(int candidateLevel, int applyId, Org org){
        if(applyId == org.getLeaderId()){
            return candidateLevel - 1;
        }
        return candidateLevel;
    }

    // 获取流程步骤
    private OaProcess getProcess(Byte billType, String stepName){
        return oaProcessService.getProcess(billType, stepName);
    }

}
