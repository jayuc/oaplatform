package top.jayu.oa.controller;

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
import top.jayu.oa.service.OrgService;
import top.jayu.oa.util.ResultUtil;

import java.util.List;
import java.util.Map;

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
    public List<OrgTree> orgTree(){
        return orgService.orgTree();
    }

    @GetMapping("/list")
    public Map<String, Object> list(Org dto){
        ResultUtil.Result result = ResultUtil.build();
        PageHelper.startPage(dto.getPageNumber(), dto.getPageSize());
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
