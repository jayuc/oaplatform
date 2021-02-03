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
            float nextOrgPrivLen = nextProcess.getOrgPrivLen();
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
            Float level = generateLevel(3, applyId, org, bill);
            map.put("level", level);
            map.put("org", org);
            return map;
        }else {
            if(length == 8){
                if(org.getYesLeader() != null && org.getYesLeader() == 1){   // 领导机构
                    Float level = generateLevel(3, applyId, org, bill);
                    map.put("level", level);
                    map.put("org", org);
                    return map;
                }
                Float level = generateLevel(5, applyId, org, bill);
                map.put("level", level);
                map.put("org", org);
                return map;
            }else if(length > 8){
                int level = 5 + (length - 8)/2;
                Float lev = generateLevel(level, applyId, org, bill);
                map.put("level", lev);
                map.put("org", org);
                return map;
            }
        }
        map.put("level", -1);
        map.put("org", org);
        return map;
    }

    public Level computeOrgLevels(OaBill bill, OaProcess p){
        Level level1 = getLevel(p, bill);
        if(level1 != null){
            return level1;
        }
        Map<String, Object> map = computeOrgLevel(bill);
        Level l = new Level();
        float level = (float) map.get("level");
        l.level = level;
        if(level == 0){
            return l;
        }
        String applyOrgCodePriv = bill.getApplyOrgCodePriv();
        String candidateOrgCodePriv = applyOrgCodePriv;
        if(level == 1){
            OaProcessFunctionResult functionResult = orgService.findCompanyLeader(bill);
            l.approveId = functionResult.getApproveList();
            l.approveName = functionResult.getApproveNameList();
            l.stepName = "市局（公司）负责人";
        }else if(level == 2){
            candidateOrgCodePriv = applyOrgCodePriv.substring(0, 6);
            OaProcessFunctionResult functionResult1 = orgService.findCompanyDeputy(bill);
            l.approveId = functionResult1.getApproveList();
            l.approveName = functionResult1.getApproveNameList();
            l.stepName = "市局（公司）分管领导";
        }else if(level == 3){
            candidateOrgCodePriv = applyOrgCodePriv.substring(0, 6);
            OaProcessFunctionResult functionResult2 = orgService.findUnitLeader(bill);
            l.approveId = functionResult2.getApproveList();
            l.approveName = functionResult2.getApproveNameList();
            l.stepName = "部门/单位负责人";
        }else if(level == 4){
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
        }else if(level == 5){
            candidateOrgCodePriv = applyOrgCodePriv.substring(0, 8);
            Org org = orgMapper.findOrgLeaderByPriv(candidateOrgCodePriv);
            l.approveId = "," + org.getLeaderId() + ",";
            l.approveName = org.getLeaderName();
            l.stepName = "二级部门负责人";
        }else if(level == 6){
            candidateOrgCodePriv = applyOrgCodePriv.substring(0, 10);
            Org org1 = orgMapper.findOrgLeaderByPriv(candidateOrgCodePriv);
            l.approveId = "," + org1.getLeaderId() + ",";
            l.approveName = org1.getLeaderName();
            l.stepName = "三级部门负责人";
        }else if(level == 7){
            candidateOrgCodePriv = applyOrgCodePriv.substring(0, 12);
            Org org2 = orgMapper.findOrgLeaderByPriv(candidateOrgCodePriv);
            l.approveId = "," + org2.getLeaderId() + ",";
            l.approveName = org2.getLeaderName();
            l.stepName = "四级部门负责人";
        }else {
            float le = l.level;
            int lc = (int) le;
            OaProcess process = new OaProcess();
            process.setBillType(bill.getBillType());
            process.setOrgPrivLen((float) lc);
            OaProcess process1 = null;
            List<OaProcess> processList = oaProcessService.getProcessByOrgPrivLen(process);
            if(processList.size() == 1){
                process1 = processList.get(0);
            }else {
                String pre = bill.getApplyOrgYesOffice() == 1 ? "0000" : "0001";
                for (OaProcess ppp:processList){
                    if(ppp.getCurrentStep().startsWith(pre)){
                        process1 = ppp;
                        break;
                    }
                }
            }
            OaProcessFunctionResult approve = findApprove(process1.getNextApproveFunctionId(), bill);
            l.approveId = approve.getApproveList();
            l.approveName = approve.getApproveNameList();
        }
        // 当为负责人的时候需更改为上级机构的机构权限
        bill.setApplyOrgCodePriv(candidateOrgCodePriv);
        return l;
    }

    @ToString
    public static class Level{
        public float level;
        public String approveId;
        public String currentStep;
        public String stepName;
        public String approveName;
    }

    private Level getLevel(OaProcess p, OaBill bill){
        Level level = new Level();
        if(!StrUtil.isBlank(p.getNextStep())){
            if("end".equals(p.getNextStep())){
                level.level = 0;
                return level;
            }
            level.currentStep = bill.getCurrentStep() + p.getNextStep();
            OaProcessFunctionResult approve = findApprove(p.getNextApproveFunctionId(), bill);
            level.approveId = approve.getApproveList();
            level.approveName = approve.getApproveNameList();
            OaProcess process2 = oaProcessService.getProcess(bill.getBillType(), level.currentStep);
            level.stepName = process2.getProcessDesc();
            level.level = 1000;
            return level;
        }
        return null;
    }

    // 第三步：查询审批人
    private OaProcessFunctionResult findApprove(int approveFunctionId, OaBill bill){
        OaProcessFunction processFunction = oaProcessFunctionService.getById(approveFunctionId);
        String serviceName = processFunction.getIocEntityName();
        String methodName = processFunction.getIocEntityMethod();
        String propertyName = processFunction.getInput();
        try {
            Object obj = bill;
            if(!"OaBill".equals(propertyName)){
                obj = springInvokeMethod.invokeProperty(bill, propertyName);
            }
            OaProcessFunctionResult oaProcessFunctionResult = (OaProcessFunctionResult) springInvokeMethod.invokeMethod(serviceName, methodName, new Object[]{obj});
            return oaProcessFunctionResult;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private Float generateLevel(float candidateLevel, int applyId, Org org, OaBill bill){
        if(applyId == org.getLeaderId()){
            if(candidateLevel == 3 && bill.getApplyOrgYesOffice() != 1){ // 2021-02-03 客户提出：直属单位 局长（负责人）申请，直接跳到 公司负责人（梁跃华）
                return candidateLevel - 2;
            }else {
                float nextApproveIdStrLevel = getOriginNextApproveId(candidateLevel, bill.getBillType(), bill.getApplyOrgYesOffice());
                if(candidateLevel - nextApproveIdStrLevel > 1){
                    return candidateLevel - 1;
                }else {
                    return nextApproveIdStrLevel;
                }
            }
        }
        return candidateLevel;
    }

    private float getOriginNextApproveId(float candidateLevel, Integer billType, Byte applyOrgYesOffice){
        OaProcess process = new OaProcess();
        process.setBillType(billType);
        process.setOrgPrivLen(candidateLevel);
        OaProcess process1 = null;
        List<OaProcess> processList = oaProcessService.getProcessByOrgPrivLen(process);
        if(processList.size() == 1){
            process1 = processList.get(0);
        }else {
            String pre = applyOrgYesOffice == 1 ? "0000" : "0001";
            for (OaProcess p:processList){
                if(p.getCurrentStep().startsWith(pre)){
                    process1 = p;
                    break;
                }
            }
        }
        String nextStep = process1.getCurrentStep() + process1.getNextStep();
        if("end".equals(nextStep)){
            return 0;
        }
        OaProcess process2 = oaProcessService.getProcess(billType, nextStep);
        return process2.getOrgPrivLen();
    }

    // 获取流程步骤
    private OaProcess getProcess(Integer billType, String stepName){
        return oaProcessService.getProcess(billType, stepName);
    }

}
