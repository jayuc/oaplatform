package top.jayu.oa.controller;

import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.jayu.oa.entity.MenuFunctionTree;
import top.jayu.oa.entity.Role;
import top.jayu.oa.entity.RolePermission;
import top.jayu.oa.iter.SaveLog;
import top.jayu.oa.mapper.RoleMapper;
import top.jayu.oa.mapper.RolePermissionMapper;
import top.jayu.oa.service.RoleService;
import top.jayu.oa.util.ResultUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by 余杰 on 2021/1/4 16:39
 */

@RestController
@RequestMapping("/role")
public class RoleController {

    @Autowired
    RoleMapper roleMapper;
    @Autowired
    RoleService roleService;
    @Autowired
    RolePermissionMapper rolePermissionMapper;

    @SaveLog(content = "角色查询")
    @GetMapping("/list")
    public Map<String, Object> list(Role dto){
        ResultUtil.Result result = ResultUtil.build();
        PageHelper.startPage(dto.getPageNumber(), dto.getPageSize());
        Page<Role> page = (Page<Role>) roleMapper.list(dto);
        result.total(page.getTotal());
        result.rows(page.getResult());
        return result.getResult();
    }

    @GetMapping("/getMenuFunctionTree")
    public List<MenuFunctionTree> getMenuFunctionTree(){
        return roleService.menuFunctionTree();
    }

    @SaveLog(content = "角色新增")
    @PostMapping("/add")
    @Transactional
    public int add(Role dto){
        int addResult = roleMapper.add(dto);
        if(addResult == 1){
            Integer roleId = dto.getRoleId();
            if(!StrUtil.isBlank(dto.getFunctionListJson())){
                List<RolePermission> permissions = new ArrayList<>();
                JSONArray jsonArray = JSONUtil.parseArray(dto.getFunctionListJson());
                jsonArray.forEach((json) -> {
                    JSONObject jsonObject = (JSONObject) json;
                    RolePermission permission = new RolePermission();
                    permission.setRoleId(roleId);
                    permission.setFunctionId(jsonObject.getInt("functionId"));
                    permissions.add(permission);
                });
                if(permissions.size() > 0){
                    rolePermissionMapper.batchAdd(permissions);
                }
            }
        }
        return addResult;
    }

    @GetMapping("/getPermission")
    public List<RolePermission> getPermission(Role dto){
        RolePermission permission = new RolePermission();
        permission.setRoleId(dto.getRoleId());
        return rolePermissionMapper.list(permission);
    }

    @SaveLog(content = "角色删除")
    @PostMapping("/delete")
    public int delete(Role dto){
        int deleteResult = roleMapper.delete(dto);
        if(deleteResult == 1){
            Integer roleId = dto.getRoleId();
            rolePermissionMapper.deleteByRoleId(roleId);
        }
        return deleteResult;
    }

    @SaveLog(content = "角色编辑")
    @PostMapping("/update")
    @Transactional
    public int update(Role dto){
        int updateResult = roleMapper.update(dto);
        if(updateResult == 1){
            Integer roleId = dto.getRoleId();
            Integer deleteResult = rolePermissionMapper.deleteByRoleId(roleId);
            if(!StrUtil.isBlank(dto.getFunctionListJson())){
                List<RolePermission> permissions = new ArrayList<>();
                JSONArray jsonArray = JSONUtil.parseArray(dto.getFunctionListJson());
                jsonArray.forEach((json) -> {
                    JSONObject jsonObject = (JSONObject) json;
                    RolePermission permission = new RolePermission();
                    permission.setRoleId(roleId);
                    permission.setFunctionId(jsonObject.getInt("functionId"));
                    permissions.add(permission);
                });
                if(permissions.size() > 0){
                    rolePermissionMapper.batchAdd(permissions);
                }
            }
        }
        return updateResult;
    }

    @SaveLog(content = "角色批量删除")
    @PostMapping("/batchDelete")
    public int batchDelete(List<Integer> ids){
        return roleMapper.batchDelete(ids);
    }

}
