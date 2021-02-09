package top.jayu.oa.controller;

import java.util.List;
import java.util.Map;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import top.jayu.oa.entity.OaBillCustomerField;
import top.jayu.oa.mapper.OaBillCustomerFieldMapper;
import top.jayu.oa.iter.SaveLog;
import top.jayu.oa.util.ResultUtil;



/**
 * 自定义流程字段
 *
 * @author yujie
 * @email 402842327@qq.com
 * @date 2021-02-09 18:31:55
 */
@RestController
@RequestMapping("/oa/bill/customer/field")
public class OaBillCustomerFieldController {

    @Autowired
    OaBillCustomerFieldMapper oaBillCustomerFieldMapper;

    @SaveLog(content = "自定义流程字段查询")
    @GetMapping("/select")
    public Map<String, Object> select(OaBillCustomerField dto){
        ResultUtil.Result result = ResultUtil.build();
        PageHelper.startPage(dto.getPageNumber(), dto.getPageSize());
        Page<OaBillCustomerField> page = (Page<OaBillCustomerField>) oaBillCustomerFieldMapper.list(dto);
        result.rows(page.getResult());
        result.total(page.getTotal());
        return result.getResult();
    }

    @SaveLog(content = "自定义流程字段新增")
    @PostMapping("/add")
    public Integer add(OaBillCustomerField dto){
        return oaBillCustomerFieldMapper.add(dto);
    }

    @SaveLog(content = "自定义流程字段编辑")
    @PostMapping("/update")
    public Integer update(OaBillCustomerField dto){
        return oaBillCustomerFieldMapper.update(dto);
    }

    @SaveLog(content = "自定义流程字段删除")
    @PostMapping("/delete")
    public Integer delete(Integer id){
        return oaBillCustomerFieldMapper.delete(id);
    }

    @SaveLog(content = "自定义流程字段批量删除")
    @PostMapping("/batchDelete")
    public Integer batchDelete(List<Integer> ids){
        return oaBillCustomerFieldMapper.batchDelete(ids);
    }

}
