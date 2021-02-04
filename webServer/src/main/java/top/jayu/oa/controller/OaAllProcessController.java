package top.jayu.oa.controller;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.jayu.oa.entity.OaAllProcess;
import top.jayu.oa.mapper.OaAllProcessMapper;
import top.jayu.oa.util.ResultUtil;

import java.util.Map;

@RestController
@RequestMapping("/oa/all/process")
public class OaAllProcessController {

    @Autowired
    OaAllProcessMapper oaAllProcessMapper;

    @GetMapping("/select")
    public Map<String, Object> select(OaAllProcess dto){
        ResultUtil.Result result = ResultUtil.build();
        PageHelper.startPage(dto.getPageNumber(), dto.getPageSize());
        Page<OaAllProcess> page = (Page<OaAllProcess>) oaAllProcessMapper.list(dto);
        result.rows(page.getResult());
        result.total(page.getTotal());
        return result.getResult();
    }

    @PostMapping("/add")
    public Integer add(OaAllProcess dto){
        return oaAllProcessMapper.add(dto);
    }

    @PostMapping("/update")
    public Integer update(OaAllProcess dto){
        return oaAllProcessMapper.update(dto);
    }

    @PostMapping("/delete")
    public Integer delete(Integer oneProcessId){
        return oaAllProcessMapper.delete(oneProcessId);
    }

}
