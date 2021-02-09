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

import top.jayu.oa.entity.OaProcessStep;
import top.jayu.oa.mapper.OaProcessStepMapper;
import top.jayu.oa.iter.SaveLog;
import top.jayu.oa.util.ResultUtil;



/**
 * 流程步骤
 *
 * @author yujie
 * @email 402842327@qq.com
 * @date 2021-02-08 16:11:01
 */
@RestController
@RequestMapping("/oa/process/step")
public class OaProcessStepController {

    @Autowired
    OaProcessStepMapper oaProcessStepMapper;

    @SaveLog(content = "流程步骤查询")
    @GetMapping("/select")
    public Map<String, Object> select(OaProcessStep dto){
        ResultUtil.Result result = ResultUtil.build();
        PageHelper.startPage(dto.getPageNumber(), dto.getPageSize());
        Page<OaProcessStep> page = (Page<OaProcessStep>) oaProcessStepMapper.list(dto);
        result.rows(page.getResult());
        result.total(page.getTotal());
        return result.getResult();
    }

    @SaveLog(content = "自定义流程生成")
    @PostMapping("/generate/process")
    public Map<String, Object> generateProcess(String jsonStr){
        return null;
    }

    @SaveLog(content = "自定义流程编辑")
    @PostMapping("/update/process")
    public Map<String, Object> updateProcess(String jsonStr){
        return null;
    }

    @GetMapping("/get/customer/process")
    public Map<String, Object> getCustomerProcess(String type){
        return null;
    }

    @SaveLog(content = "流程步骤新增")
    @PostMapping("/add")
    public Integer add(OaProcessStep dto){
        return oaProcessStepMapper.add(dto);
    }

    @SaveLog(content = "流程步骤编辑")
    @PostMapping("/update")
    public Integer update(OaProcessStep dto){
        return oaProcessStepMapper.update(dto);
    }

    @SaveLog(content = "流程步骤删除")
    @PostMapping("/delete")
    public Integer delete(Integer id){
        return oaProcessStepMapper.delete(id);
    }

    @SaveLog(content = "流程步骤批量删除")
    @PostMapping("/batchDelete")
    public Integer batchDelete(List<Integer> ids){
        return oaProcessStepMapper.batchDelete(ids);
    }

}
