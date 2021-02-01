package top.jayu.oa.controller;

import cn.hutool.core.util.StrUtil;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.jayu.oa.entity.Org;
import top.jayu.oa.entity.OrgTree;
import top.jayu.oa.mapper.OrgMapper;
import top.jayu.oa.param.BaseParam;
import top.jayu.oa.service.OrgService;
import top.jayu.oa.util.ResultUtil;

import java.util.*;

/**
 * Created by 余杰 on 2020/12/16 11:50
 */

@RestController
@RequestMapping("/org")
public class OrgController {

    @Autowired
    OrgService orgService;
    @Autowired
    OrgMapper orgMapper;

    @GetMapping("/orgTree")
    public List<OrgTree> orgTree(Org dto){
        List<OrgTree> orgTrees = orgService.orgTree();
        String upperLoginName = dto.getCurrentLoginName().toUpperCase();
        if("ADMIN".equals(upperLoginName)){
            return orgTrees;
        }
        // 如果有机构权限则使用机构权限，如果没有则使用默认机构权限
        String orgCodePriv = dto.getOrgCodePriv();
        if(StrUtil.isBlank(orgCodePriv)){
            orgCodePriv = dto.getCurrentOrgCodePriv();
        }
        // 查询机构树的时候，查询本单位（部门）下面的机构树
        orgCodePriv = orgCodePriv.substring(0, 6);
        Map<String, OrgTree> stringOrgTreeMap = new HashMap<>();
        loopOrg(orgTrees, stringOrgTreeMap);
        OrgTree tree = stringOrgTreeMap.get(orgCodePriv);
        return Arrays.asList(tree);
    }

    private void loopOrg(List<OrgTree> list, Map<String, OrgTree> map){
        for (OrgTree tree:list){
            if(tree.getAttribute() != null && StrUtil.isNotBlank(tree.getAttribute().getOrgCodePriv())){
                map.put(tree.getAttribute().getOrgCodePriv(), tree);
            }
            loopOrg(tree.getChildren(), map);
        }
    }

    @GetMapping("/list")
    public Map<String, Object> list(Org dto){
        ResultUtil.Result result = ResultUtil.build();
        PageHelper.startPage(dto.getPageNumber(), dto.getPageSize());
        // 默认查询所属机构下属机构
        if(StrUtil.isBlank(dto.getOrgCodePriv())){
            dto.setOrgCodePriv(dto.getCurrentOrgCodePriv());
        }
        Page<Org> page = (Page<Org>) orgMapper.list(dto);
        result.total(page.getTotal());
        result.rows(page.getResult());
        return result.getResult();
    }

    @PostMapping("/add")
    public Integer add(Org dto){
        return orgService.add(dto);
    }

    @PostMapping("/update")
    public Integer update(Org dto){
        return orgMapper.update(dto);
    }

    @PostMapping("/delete")
    public Integer delete(Integer orgId){
        return orgMapper.delete(orgId);
    }

}
