package top.jayu.oa.controller;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.jayu.oa.entity.OaBill;
import top.jayu.oa.mapper.OaBillMapper;
import top.jayu.oa.param.OaBillParam;
import top.jayu.oa.util.ResultUtil;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/oa/bill")
public class OaBillController {

    @Autowired
    OaBillMapper oaBillMapper;

    @GetMapping("/list")
    public Map<String, Object> list(OaBill dto){
        ResultUtil.Result result = ResultUtil.build();
        PageHelper.startPage(dto.getPageNumber(), dto.getPageSize());
        if(dto != null && dto.getUserId() != -1 && dto.getUserId() != 1){
            dto.setCurrentStep(dto.getUserId() + "");
        }
        List<OaBill> list = oaBillMapper.list(dto);
        if(list.size() > 0){
            Page<OaBill> page = (Page<OaBill>) list;
            result.rows(page.getResult());
            result.total(page.getTotal());
        }
        return result.getResult();
    }

    @PostMapping("/insert")
    public int insert(OaBill dto){
        return oaBillMapper.insert(dto);
    }

    @PostMapping("/deleteById")
    public int deleteById(String id){
        return oaBillMapper.deleteById(id);
    }

    @PostMapping("/update")
    public int update(OaBill dto){
        return oaBillMapper.update(dto);
    }

    @PostMapping("/approve")
    public int approve(OaBillParam dto){
        return oaBillMapper.approve(dto);
    }

}
