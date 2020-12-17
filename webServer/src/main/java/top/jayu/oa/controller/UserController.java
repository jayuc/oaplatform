package top.jayu.oa.controller;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.jayu.oa.entity.User;
import top.jayu.oa.mapper.EmployMapper;
import top.jayu.oa.mapper.UserMapper;
import top.jayu.oa.param.LoginUser;
import top.jayu.oa.util.ResultUtil;

import javax.validation.Valid;
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

    @GetMapping("/getUserById")
    public User getUserById(Integer id){
        return userMapper.getUserById(id);
    }

    @GetMapping("/list")
    public Map<String, Object> list(User dto){
        ResultUtil.Result result = ResultUtil.build();
        PageHelper.startPage(dto.getPageNumber(), dto.getPageSize());
        Page<User> page = (Page<User>) userMapper.list(dto);
        result.total(page.getTotal());
        result.rows(page.getResult());
        return result.getResult();
    }

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

}
