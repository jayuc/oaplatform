package top.jayu.oa.controller;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.jayu.oa.entity.OaBillOpera;
import top.jayu.oa.mapper.OaBillOperaMapper;
import top.jayu.oa.util.ResultUtil;

import java.util.List;
import java.util.Map;

/**
 * Created by 余杰 on 2021/1/4 17:31
 */

@RestController
@RequestMapping("/oa/bill/opera")
public class OaBillOperaController {

    @Autowired
    OaBillOperaMapper oaBillOperaMapper;

    @GetMapping("/list")
    public Map<String, Object> list(OaBillOpera dto){
        ResultUtil.Result result = ResultUtil.build();
        PageHelper.startPage(dto.getPageNumber(), dto.getPageSize());
        Page<OaBillOpera> page = (Page<OaBillOpera>) oaBillOperaMapper.list(dto);
        result.total(page.getTotal());
        result.rows(page.getResult());
        return result.getResult();
    }

    @GetMapping("/listAll")
    public List<OaBillOpera> listAll(OaBillOpera dto){
        return oaBillOperaMapper.list(dto);
    }

}
