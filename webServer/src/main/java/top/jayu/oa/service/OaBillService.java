package top.jayu.oa.service;

import cn.hutool.core.util.StrUtil;
import lombok.ToString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.jayu.oa.entity.*;
import top.jayu.oa.iter.SpringInvokeMethod;
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
    OaProcessFunctionService oaProcessFunctionService;
    @Autowired
    OrgService orgService;
    @Autowired
    SpringInvokeMethod springInvokeMethod;

    // 是否 5000元（不含）以内
    public boolean yesLess5000(OaBill bill){
        return bill.getAmount() < 5000;
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
            Level level = generateLevel(3, applyId, org, bill);
            map.put("level", level);
            map.put("org", org);
            return map;
        }else {
            if(length == 8){
                if(org.getYesLeader() != null && org.getYesLeader() == 1){   // 领导机构
                    Level level = generateLevel(3, applyId, org, bill);
                    map.put("level", level);
                    map.put("org", org);
                    return map;
                }
                Level level = generateLevel(5, applyId, org, bill);
                map.put("level", level);
                map.put("org", org);
                return map;
            }else if(length > 8){
                int level = 5 + (length - 8)/2;
                Level lev = generateLevel(level, applyId, org, bill);
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
        Level ll = (Level) map.get("level");
        if(ll.approveId != null){
            return ll;
        }
        int level = ll.level;
        l.level = level;
        String applyOrgCodePriv = bill.getApplyOrgCodePriv();
        String candidateOrgCodePriv = applyOrgCodePriv;
        switch (level){
            case 1:
                OaProcessFunctionResult functionResult = orgService.findCompanyLeader(bill);
                l.approveId = functionResult.getApproveList();
                l.approveName = functionResult.getApproveNameList();
                l.stepName = "市局负责人";
                break;
            case 2:
                candidateOrgCodePriv = applyOrgCodePriv.substring(0, 6);
                OaProcessFunctionResult functionResult1 = orgService.findCompanyDeputy(bill);
                l.approveId = functionResult1.getApproveList();
                l.approveName = functionResult1.getApproveNameList();
                l.stepName = "市局分管领导";
                break;
            case 3:
                candidateOrgCodePriv = applyOrgCodePriv.substring(0, 6);
                OaProcessFunctionResult functionResult2 = orgService.findUnitLeader(bill);
                l.approveId = functionResult2.getApproveList();
                l.approveName = functionResult2.getApproveNameList();
                l.stepName = "部门/单位负责人";
                break;
            case 4:
                if(bill.getApplyOrgYesOffice() == 1){
                    l.level = 3;
                    candidateOrgCodePriv = applyOrgCodePriv.substring(0, 6);
                    OaProcessFunctionResult functionResult3 = orgService.findUnitLeader(bill);
                    l.approveId = functionResult3.getApproveList();
                    l.approveName = functionResult3.getApproveNameList();
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
                        OaProcessFunctionResult functionResult3 = orgService.findUnitDeputy(bill);
                        l.approveId = functionResult3.getApproveList();
                        l.approveName = functionResult3.getApproveNameList();
                        l.stepName = "单位分管领导";
                    }else {
                        l.level = 3;
                        candidateOrgCodePriv = applyOrgCodePriv.substring(0, 6);
                        OaProcessFunctionResult unitLeader = orgService.findUnitLeader(bill);
                        l.approveId = unitLeader.getApproveList();
                        l.approveName = unitLeader.getApproveNameList();
                        l.stepName = "部门/单位负责人";
                    }
                }
                break;
            case 5:
                candidateOrgCodePriv = applyOrgCodePriv.substring(0, 8);
                Org org = orgMapper.findOrgLeaderByPriv(candidateOrgCodePriv);
                l.approveId = "," + org.getLeaderId() + ",";
                l.approveName = org.getLeaderName();
                l.stepName = "二级部门负责人";
                break;
            case 6:
                candidateOrgCodePriv = applyOrgCodePriv.substring(0, 10);
                Org org1 = orgMapper.findOrgLeaderByPriv(candidateOrgCodePriv);
                l.approveId = "," + org1.getLeaderId() + ",";
                l.approveName = org1.getLeaderName();
                l.stepName = "三级部门负责人";
                break;
            case 7:
                candidateOrgCodePriv = applyOrgCodePriv.substring(0, 12);
                Org org2 = orgMapper.findOrgLeaderByPriv(candidateOrgCodePriv);
                l.approveId = "," + org2.getLeaderId() + ",";
                l.approveName = org2.getLeaderName();
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
        public String currentStep;
        public String stepName;
        public String approveName;
    }

    private Level generateLevel(int candidateLevel, int applyId, Org org, OaBill bill){
        Level level = new Level();
        if(applyId == org.getLeaderId()){
            level.level = candidateLevel - 1;
            Level nextApproveIdStrLevel = getOriginNextApproveId(candidateLevel, bill.getBillType(), bill);
            if(nextApproveIdStrLevel != null){
                int nextApproveId = Integer.valueOf(nextApproveIdStrLevel.approveId.substring(1,nextApproveIdStrLevel.approveId.length()-1));
                if(applyId == nextApproveId){
                    level.level = candidateLevel - 1;
                }else {
                    level.level = candidateLevel - 1;
//                    level.approveId = nextApproveIdStrLevel.approveId;
//                    level.approveName = nextApproveIdStrLevel.approveName;
//                    level.currentStep = nextApproveIdStrLevel.currentStep;
                }
                return level;
            }
        }
        level.level = candidateLevel;
        return level;
    }

    private Level getOriginNextApproveId(int candidateLevel, Byte billType, OaBill bill){
        OaProcess process = new OaProcess();
        Level level = new Level();
        process.setBillType(billType);
        process.setOrgPrivLen((byte) candidateLevel);
        OaProcess process1 = oaProcessService.getProcessByOrgPrivLen(process);
        Integer nextApproveFunctionId = process1.getNextApproveFunctionId();
        if(nextApproveFunctionId != null && nextApproveFunctionId > 0){
            OaProcessFunction processFunction = oaProcessFunctionService.getById(nextApproveFunctionId);
            String serviceName = processFunction.getIocEntityName();
            String methodName = processFunction.getIocEntityMethod();
            try {
                OaProcessFunctionResult oaProcessFunctionResult = (OaProcessFunctionResult) springInvokeMethod.invokeMethod(serviceName, methodName, new Object[]{bill});
                String approveList = oaProcessFunctionResult.getApproveList();
                level.approveId = approveList;
                level.approveName = oaProcessFunctionResult.getApproveNameList();
                level.currentStep = process1.getNextStep();
                return level;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    // 获取流程步骤
    private OaProcess getProcess(Byte billType, String stepName){
        return oaProcessService.getProcess(billType, stepName);
    }

}
