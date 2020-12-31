package top.jayu.oa.service;

import cn.hutool.core.util.StrUtil;
import lombok.ToString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.jayu.oa.entity.OaBill;
import top.jayu.oa.entity.OaProcess;
import top.jayu.oa.entity.Org;
import top.jayu.oa.mapper.OrgMapper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
        if(StrUtil.isBlank(approveOrgCodePriv)){  // 没有审批过的
            approveOrgCodePriv = bill.getApplyOrgCodePriv();
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
            String applyOrgCodePriv = bill.getApplyOrgCodePriv();
            String upOrgCodePriv = applyOrgCodePriv.substring(0, applyOrgCodePriv.length() - 2);
            Integer uporgId = orgMapper.getOrgIdByPriv(upOrgCodePriv);
            OaBill a = new OaBill();
            a.setApplyOrgId(uporgId);
            a.setApplyId(bill.getApplyId());
            a.setApplyOrgCodePriv(upOrgCodePriv);
            Map<String, Object> stringObjectMap = computeOrgLevel(a);
            if(nextOrgPrivLen == (int)stringObjectMap.get("level")){
                return true;
            }
            return false;
        }
        return false;
    }

    // 查询流程所属层级
    public Map<String, Object> computeOrgLevel(OaBill bill){
        String applyOrgCodePriv = bill.getApplyOrgCodePriv();
        int length = applyOrgCodePriv.length();
        int applyId = bill.getApplyId();
        Map<String, Object> map = new HashMap<>();
        // 申请人机构
        Org org = orgMapper.getById(bill.getApplyOrgId());
        if(length == 6){
            if("340000".equals(applyOrgCodePriv)){
                if(applyId == 2){  // 市局负责人
                    map.put("level", 0);
                    map.put("org", org);
                    return map;
                }
                map.put("level", 1);
                map.put("org", org);
                return map;
            }
            int level = generateLevel(3, applyId, org);
            map.put("level", level);
            map.put("org", org);
            return map;
        }else {
            if(length == 8){
                if(org.getYesLeader() != null && org.getYesLeader() == 1){   // 领导机构
                    int level = generateLevel(3, applyId, org);
                    map.put("level", level);
                    map.put("org", org);
                    return map;
                }
                int level = generateLevel(5, applyId, org);
                map.put("level", level);
                map.put("org", org);
                return map;
            }else if(length > 8){
                int level = 5 + (length - 8)/2;
                int lev = generateLevel(level, applyId, org);
                map.put("level", lev);
                map.put("org", org);
                return map;
            }
        }
        map.put("level", -1);
        map.put("org", org);
        return map;
    }

    public Level computeOrgLevels(OaBill bill){
        Map<String, Object> map = computeOrgLevel(bill);
        Level l = new Level();
        int level = (int) map.get("level");
        l.level = level;
        String applyOrgCodePriv = bill.getApplyOrgCodePriv();
        String candidateOrgCodePriv = applyOrgCodePriv;
        switch (level){
            case 1:
                l.approveId = orgService.findCompanyLeader(bill);
                l.stepName = "市局负责人";
                break;
            case 2:
                candidateOrgCodePriv = applyOrgCodePriv.substring(0, 6);
                l.approveId = orgService.findCompanyDeputy(bill);
                l.stepName = "市局分管领导";
                break;
            case 3:
                candidateOrgCodePriv = applyOrgCodePriv.substring(0, 6);
                l.approveId = orgService.findUnitLeader(bill);
                l.stepName = "部门/单位负责人";
                break;
            case 4:
                if(bill.getApplyOrgYesOffice() == 1){
                    l.level = 3;
                    candidateOrgCodePriv = applyOrgCodePriv.substring(0, 6);
                    l.approveId = orgService.findUnitLeader(bill);
                    l.stepName = "部门/单位负责人";
                }else {
                    OaProcess process = new OaProcess();
                    process.setBillType(bill.getBillType());
                    List<OaProcess> list = oaProcessService.list(process);
                    boolean has4 = false;
                    for (int i=0; i<list.size(); i++){
                        if(list.get(i).getOrgPrivLen() == 4){
                            has4 = true;
                        }
                    }
                    if(has4){
                        candidateOrgCodePriv = applyOrgCodePriv.substring(0, 8);
                        l.approveId = orgService.findUnitDeputy(bill);
                        l.stepName = "单位分管领导";
                    }else {
                        l.level = 3;
                        candidateOrgCodePriv = applyOrgCodePriv.substring(0, 6);
                        l.approveId = orgService.findUnitLeader(bill);
                        l.stepName = "部门/单位负责人";
                    }
                }
                break;
            case 5:
                candidateOrgCodePriv = applyOrgCodePriv.substring(0, 8);
                Integer orgLeaderByPriv = orgMapper.findOrgLeaderByPriv(candidateOrgCodePriv);
                l.approveId = "," + orgLeaderByPriv + ",";
                l.stepName = "二级部门负责人";
                break;
            case 6:
                candidateOrgCodePriv = applyOrgCodePriv.substring(0, 10);
                Integer orgLeaderByPriv1 = orgMapper.findOrgLeaderByPriv(candidateOrgCodePriv);
                l.approveId = "," + orgLeaderByPriv1 + ",";
                l.stepName = "三级部门负责人";
                break;
            case 7:
                candidateOrgCodePriv = applyOrgCodePriv.substring(0, 12);
                Integer orgLeaderByPriv2 = orgMapper.findOrgLeaderByPriv(candidateOrgCodePriv);
                l.approveId = "," + orgLeaderByPriv2 + ",";
                l.stepName = "四级部门负责人";
                break;
        }
        // 当为负责人的时候需更改为上级机构的机构权限
        bill.setApplyOrgCodePriv(candidateOrgCodePriv);
        return l;
    }

    @ToString
    public static class Level{
        public int level;
        public String approveId;
        public String stepName;
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
