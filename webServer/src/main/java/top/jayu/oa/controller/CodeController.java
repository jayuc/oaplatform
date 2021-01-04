package top.jayu.oa.controller;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.jayu.oa.entity.Code;
import top.jayu.oa.mapper.CodeMapper;
import top.jayu.oa.util.ResultUtil;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/code")
public class CodeController {

    @Autowired
    CodeMapper codeMapper;

    @GetMapping("/list")
    public List<Code> list(Code dto){
        return codeMapper.list(dto);
    }

    @GetMapping("/select")
    public Map<String, Object> select(Code dto){
        ResultUtil.Result result = ResultUtil.build();
        PageHelper.startPage(dto.getPageNumber(), dto.getPageSize());
        Page<Code> page = (Page<Code>) codeMapper.list(dto);
        result.total(page.getTotal());
        result.rows(page.getResult());
        return result.getResult();
    }

    @PostMapping("/add")
    public int add(Code dto){
        return codeMapper.add(dto);
    }

    @PostMapping("/update")
    public int update(Code dto){
        return codeMapper.update(dto);
    }

    @PostMapping("/delete")
    public int delete(Code dto){
        return codeMapper.delete(dto);
    }

    @PostMapping("/batchDelete")
    public int[] batchDelete(List<Integer> ids){
        return codeMapper.batchDelete(ids);
    }

}
