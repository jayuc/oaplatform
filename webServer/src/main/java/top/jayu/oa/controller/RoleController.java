package top.jayu.oa.controller;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.jayu.oa.entity.Role;
import top.jayu.oa.mapper.RoleMapper;
import top.jayu.oa.util.ResultUtil;

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

    @GetMapping("/list")
    public Map<String, Object> list(Role dto){
        ResultUtil.Result result = ResultUtil.build();
        PageHelper.startPage(dto.getPageNumber(), dto.getPageSize());
        Page<Role> page = (Page<Role>) roleMapper.list(dto);
        result.total(page.getTotal());
        result.rows(page.getResult());
        return result.getResult();
    }

    @PostMapping("/add")
    public int add(Role dto){
        return roleMapper.add(dto);
    }

    @PostMapping("/delete")
    public int delete(Role dto){
        return roleMapper.delete(dto);
    }

    @PostMapping("/update")
    public int update(Role dto){
        return roleMapper.update(dto);
    }

    @PostMapping("/batchDelete")
    public int[] batchDelete(List<Integer> ids){
        return roleMapper.batchDelete(ids);
    }

}
