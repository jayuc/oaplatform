package top.jayu.oa.service;

import cn.hutool.core.util.StrUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.jayu.oa.entity.*;
import top.jayu.oa.mapper.CodeMapper;
import top.jayu.oa.mapper.OrgMapper;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by 余杰 on 2020/12/16 11:02
 */

@Service
public class OrgService {

    @Autowired
    OrgMapper orgMapper;
    @Autowired
    CodeMapper codeMapper;

    /**
     * 是否是机关
     * @return
     */
    public boolean orgIfOffice(Integer orgId){
        Byte b = orgMapper.ifOffice(orgId);
        return b == 1;
    }

    // 查询机构负责人
    public int findOrgLeaderById(Integer id){
        return orgMapper.findOrgLeaderById(id);
    }

    // 查询部门负责人，
    public String findOrgLeader(Integer orgId){
        int id = findOrgLeaderById(orgId);
        if(id > 0){
            return "," + id + ",";
        }
        return null;
    }

    // 查询人力资源科负责人
    public OaProcessFunctionResult findHumanResourceLeader(OaBill oaBill){
        Org org = orgMapper.findOrgLeaderByPriv("340004");
        OaProcessFunctionResult result = new OaProcessFunctionResult();
        result.setApproveNameList(org.getLeaderName());
        result.setApproveList("," + org.getLeaderId() + ",");
        return result;
    }

    // 查询办公室 马明敏、苗倩
    public OaProcessFunctionResult findMaMIngMin(OaBill oaBill){
        OaProcessFunctionResult result = new OaProcessFunctionResult();
        result.setApproveList(",28,23,");
        result.setApproveNameList("马明敏、苗倩");
        return result;
    }

    // 查询办公室主任
    public OaProcessFunctionResult findOfficeHouseLeader(OaBill oaBill){
        Org org = orgMapper.findOrgLeaderByPriv("340001");
        OaProcessFunctionResult result = new OaProcessFunctionResult();
        result.setApproveNameList(org.getLeaderName());
        result.setApproveList("," + org.getLeaderId() + ",");
        return result;
    }

    // 查询财务科负责人
    public OaProcessFunctionResult findFinanceLeader(OaBill oaBill){
        Org org = orgMapper.findOrgLeaderByPriv("340008");
        OaProcessFunctionResult result = new OaProcessFunctionResult();
        result.setApproveNameList(org.getLeaderName());
        result.setApproveList("," + org.getLeaderId() + ",");
        return result;
    }

    // 查找上级部门负责人
    public OaProcessFunctionResult findUpOrgLeader(OaBill bill){
        String approveOrgCodePriv = bill.getApproveOrgCodePriv();
        if(StrUtil.isBlank(approveOrgCodePriv)){  // 没有审批过的
            approveOrgCodePriv = bill.getApplyOrgCodePriv();
        }
        String upOrgCodePriv = approveOrgCodePriv.substring(0, approveOrgCodePriv.length() - 2);
        Org org = orgMapper.findOrgLeaderByPriv(upOrgCodePriv);
        OaProcessFunctionResult result = new OaProcessFunctionResult();
        result.setApproveNameList(org.getLeaderName());
        result.setApproveList("," + org.getLeaderId() + ",");
        return result;
    }

    // 查询市局分管领导
    public OaProcessFunctionResult findCompanyDeputy(OaBill oaBill){
        String orgCodePriv = oaBill.getApplyOrgCodePriv();
        if(StrUtil.isBlank(orgCodePriv)){
            return null;
        }
        if(oaBill.getApplyOrgYesOffice() == 1){
            String orgPriv = orgCodePriv.substring(0, 6);
            Org org = orgMapper.findOrgDeputyByPriv(orgPriv);
            OaProcessFunctionResult result = new OaProcessFunctionResult();
            result.setApproveNameList(org.getDeputyName());
            result.setApproveList("," + org.getDeputyId() + ",");
            return result;
        }
        Byte firmType = oaBill.getFirmType();
        Code code = new Code();
        code.setCode((short) 5);
        code.setCodeNo(firmType);
        Code codeList = codeMapper.getCode(code);
        String codeName = codeList.getName();
        Org orgByFirmTypeName = orgMapper.getOrgByFirmTypeName(codeName);
        OaProcessFunctionResult result = new OaProcessFunctionResult();
        result.setApproveList("," + orgByFirmTypeName.getDeputyId() + ",");
        result.setApproveNameList(orgByFirmTypeName.getDeputyName());
        return result;
    }

    // 查询市局负责人
    public OaProcessFunctionResult findCompanyLeader(OaBill oaBill){
        Org org = orgMapper.findOrgLeaderByPriv("340000");
        OaProcessFunctionResult result = new OaProcessFunctionResult();
        result.setApproveNameList(org.getLeaderName());
        result.setApproveList("," + org.getLeaderId() + ",");
        return result;
    }

    // 查询单位分管领导
    public OaProcessFunctionResult findUnitDeputy(OaBill oaBill){
        String orgCodePriv = oaBill.getApplyOrgCodePriv();
        if(StrUtil.isBlank(orgCodePriv)){
            return null;
        }
        String orgPriv = orgCodePriv.substring(0, 8);
        Org org = orgMapper.findOrgDeputyByPriv(orgPriv);
        OaProcessFunctionResult result = new OaProcessFunctionResult();
        result.setApproveNameList(org.getDeputyName());
        result.setApproveList("," + org.getDeputyId() + ",");
        return result;
    }

    // 查询单位负责人
    public OaProcessFunctionResult findUnitLeader(OaBill oaBill){
        String orgCodePriv = oaBill.getApplyOrgCodePriv();
        if(StrUtil.isBlank(orgCodePriv)){
            return null;
        }
        String orgPriv = orgCodePriv.substring(0, 6);
        Org org = orgMapper.findOrgLeaderByPriv(orgPriv);
        OaProcessFunctionResult result = new OaProcessFunctionResult();
        result.setApproveNameList(org.getLeaderName());
        result.setApproveList("," + org.getLeaderId() + ",");
        return result;
    }

    public List<OrgTree> orgTree(){

        OrgTree root = new OrgTree(-1, "3400", "合肥市烟草专卖局", null);
        List<Org> list1 = orgMapper.list1();
        List<Org> list2 = orgMapper.list2();
        List<Org> list3 = orgMapper.list3();
        List<Org> list4 = orgMapper.list4();
        Map<Integer, OrgTree> tempMap1 = new HashMap<>();
        Map<Integer, OrgTree> tempMap2 = new HashMap<>();
        Map<Integer, OrgTree> tempMap3 = new HashMap<>();
        Map<Integer, OrgTree> tempMap4 = new HashMap<>();

        list1.forEach((org) -> {
            OrgTree tree = new OrgTree(org.getOrgId(), org.getOrgCode(), org.getOrgName(), org);
            root.getChildren().add(tree);
            tempMap1.put(org.getOrgId(), tree);
        });
        handleList(list2, tempMap1, tempMap2);
        handleList(list3, tempMap2, tempMap3);
        handleList(list4, tempMap3, tempMap4);

        return Arrays.asList(root);
    }

    private void handleList(List<Org> list, Map<Integer, OrgTree> tempMap1, Map<Integer, OrgTree> tempMap2){
        list.forEach((org -> {
            OrgTree tree = new OrgTree(org.getOrgId(), org.getOrgCode(), org.getOrgName(), org);
            OrgTree ptree = tempMap1.get(org.getParentId());
            if(ptree != null){
                ptree.getChildren().add(tree);
                tempMap2.put(org.getOrgId(), tree);
            }
        }));
    }

}
