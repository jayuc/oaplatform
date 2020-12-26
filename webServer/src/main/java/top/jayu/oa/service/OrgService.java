package top.jayu.oa.service;

import cn.hutool.core.util.StrUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.jayu.oa.entity.Org;
import top.jayu.oa.entity.OrgTree;
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

    // 查询市局分管领导
    public String findCompanyDeputy(String orgCodePriv){
        if(StrUtil.isBlank(orgCodePriv)){
            return null;
        }
        String orgPriv = orgCodePriv.substring(0, 6);
        Integer deputyId = orgMapper.findOrgDeputyByPriv(orgPriv);
        return "," + deputyId + ",";
    }

    // 查询市局负责人
    public String findCompanyLeader(String orgCodePriv){
        Integer deputyId = orgMapper.findOrgLeaderByPriv("340000");
        return "," + deputyId + ",";
    }

    // 查询单位分管领导
    public String findUnitDeputy(String orgCodePriv){
        if(StrUtil.isBlank(orgCodePriv)){
            return null;
        }
        String orgPriv = orgCodePriv.substring(0, 8);
        Integer deputyId = orgMapper.findOrgDeputyByPriv(orgPriv);
        return "," + deputyId + ",";
    }

    // 查询单位负责人
    public String findUnitLeader(String orgCodePriv){
        if(StrUtil.isBlank(orgCodePriv)){
            return null;
        }
        String orgPriv = orgCodePriv.substring(0, 6);
        Integer deputyId = orgMapper.findOrgLeaderByPriv(orgPriv);
        return "," + deputyId + ",";
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
