package top.jayu.oa.controller;

import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.jayu.oa.entity.Org;
import top.jayu.oa.entity.User;
import top.jayu.oa.entity.UserRole;
import top.jayu.oa.iter.SaveLog;
import top.jayu.oa.mapper.EmployMapper;
import top.jayu.oa.mapper.OrgMapper;
import top.jayu.oa.mapper.UserMapper;
import top.jayu.oa.mapper.UserRoleMapper;
import top.jayu.oa.param.LoginUser;
import top.jayu.oa.util.ResultUtil;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserMapper userMapper;
    @Autowired
    EmployMapper employMapper;
    @Autowired
    OrgMapper orgMapper;
    @Autowired
    UserRoleMapper userRoleMapper;

    @GetMapping("/getUserById")
    public User getUserById(Integer id){
        return userMapper.getUserById(id);
    }

    @SaveLog(content = "用户查询")
    @GetMapping("/list")
    public Map<String, Object> list(User dto){
        ResultUtil.Result result = ResultUtil.build();
        PageHelper.startPage(dto.getPageNumber(), dto.getPageSize());
        Page<User> page = (Page<User>) userMapper.list(dto);
        result.total(page.getTotal());
        result.rows(page.getResult());
        return result.getResult();
    }

    @GetMapping("/listAll")
    public List<User> listAll(){
        User dto = new User();
        return userMapper.list(dto);
    }

    @SaveLog(content = "用户新增")
    @PostMapping("/add")
    @Transactional
    public Integer add(User dto){
        int addResult = userMapper.add(dto);
        if(addResult == 1){
            List<UserRole> roleList = convert(dto.getRoleJson(), dto.getUserId());
            if(roleList.size() > 0){
                userRoleMapper.batchAdd(roleList);
            }
        }
        return addResult;
    }

    @SaveLog(content = "用户编辑")
    @PostMapping("/update")
    @Transactional
    public Integer update(User dto){
        int updateResult = userMapper.update(dto);
        if(updateResult == 1){
            userRoleMapper.deleteByUserId(dto.getUserId());
            List<UserRole> roleList = convert(dto.getRoleJson(), dto.getUserId());
            if(roleList.size() > 0){
                userRoleMapper.batchAdd(roleList);
            }
        }
        return updateResult;
    }

    private List<UserRole> convert(String roleJson, int userId){
        List<UserRole> list = new ArrayList<>();
        if(!StrUtil.isBlank(roleJson)){
            JSONArray jsonArray = JSONUtil.parseArray(roleJson);
            jsonArray.forEach((json) -> {
                JSONObject jsonObject = (JSONObject) json;
                UserRole userRole = new UserRole();
                userRole.setRoleId(jsonObject.getInt("roleId"));
                userRole.setUserId(userId);
                list.add(userRole);
            });
        }
        return list;
    }

    @SaveLog(content = "用户登录")
    @PostMapping("/login")
    public Map<String, Object> login(@Valid LoginUser user){
        log.info(user.toString());
        ResultUtil.Result result = ResultUtil.build();
        String password = userMapper.getPasswordByLoginName(user.getLoginName());
        if(password != null){
            if(!user.getPassword().equals(password)){
                result.error("用户名或密码错误！");
            }else {
                result.property("user", userMapper.getUserByLoginName(user.getLoginName()));
            }
        }else {
            result.error("用户名错误！");
        }
        return result.getResult();
    }

    @SaveLog(content = "用户登出")
    @PostMapping("/loginOut")
    public Map<String, Object> loginOut(LoginUser user){
        ResultUtil.Result result = ResultUtil.build();
        return result.getResult();
    }

    @SaveLog(content = "用户密码修改")
    @PostMapping("/updatePassword")
    public Map<String, Object> updatePassword(LoginUser dto){
        ResultUtil.Result result = ResultUtil.build();
        String password = userMapper.getPasswordByLoginName(dto.getLoginName());
        if(!StrUtil.isBlank(password) && password.equals(dto.getOriginPassword())){
            Integer res = userMapper.updatePassword(dto);
            if(res != 1){
                result.error("密码修改错误");
            }
        }else {
            result.error("原密码输入错误");
        }
        return result.getResult();
    }

    // 获取此部门下面的负责人待选
    @GetMapping("/getLeaderCandidate")
    public Map<String, List<User>> getLeaderCandidate(User dto){
        Map<String, List<User>> map = new HashMap<>();
        generateUserList(map, dto.getOrgId(), dto.getOrgName());
        Org orgDto = new Org();
        orgDto.setParentId(dto.getOrgId());
        List<Org> orgList = orgMapper.list(orgDto);
        for (int i=0; i<orgList.size(); i++){
            Org item = orgList.get(i);
            if(item.getOrgName().indexOf("领导") > -1){
                generateUserList(map, item.getOrgId(), item.getOrgName());
            }
        }
        return map;
    }

    // 获取此部门下面的分管领导待选
    @GetMapping("/getDeputyCandidate")
    public Map<String, List<User>> getDeputyCandidate(User dto){
        Org org = orgMapper.getById(dto.getOrgId());
        Org parentOrg = orgMapper.getById(org.getParentId());
        if(parentOrg == null){
            Map<String, List<User>> map = new HashMap<>();
            generateUserList(map, 1, "市局（公司）领导");
            return map;
        }else {
            User userDto = new User();
            userDto.setOrgId(parentOrg.getOrgId());
            userDto.setOrgName(parentOrg.getOrgName());
            return getLeaderCandidate(userDto);
        }
    }

    @GetMapping("/getRoleById")
    public List<UserRole> getRoleById(Integer userId){
        return userRoleMapper.getRoleByUserId(userId);
    }

    @PostMapping("/deleteOne")
    @Transactional
    public int deleteOne(Integer id){
        int deleteResult = userMapper.deleteOne(id);
        if(deleteResult == 1){
            userRoleMapper.deleteByUserId(id);
        }
        return deleteResult;
    }

    @SaveLog(content = "用户密码重置")
    @PostMapping("/resetPassword")
    public int resetPassword(LoginUser dto){
        return userMapper.resetPassword(dto);
    }

    private void generateUserList(Map<String, List<User>> map, Integer orgId, String orgName){
        User userDto = new User();
        userDto.setOrgId(orgId);
        List<User> list = userMapper.list(userDto);
        if(list.size() > 0){
            map.put(orgName, list);
        }
    }

}
