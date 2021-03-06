package top.jayu.oa.controller;

import cn.hutool.core.util.StrUtil;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.jayu.oa.entity.Code;
import top.jayu.oa.entity.OaBill;
import top.jayu.oa.entity.OaBillPending;
import top.jayu.oa.iter.WorkFlowEngine;
import top.jayu.oa.mapper.CodeMapper;
import top.jayu.oa.mapper.OaBillMapper;
import top.jayu.oa.param.BaseParam;
import top.jayu.oa.param.OaBillParam;
import top.jayu.oa.util.ResultUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/oa/bill")
public class OaBillController {

    @Autowired
    OaBillMapper oaBillMapper;
    @Autowired
    CodeMapper codeMapper;
    @Autowired
    WorkFlowEngine workFlowEngine;

    @GetMapping("/list")
    public Map<String, Object> list(OaBill dto){
        ResultUtil.Result result = ResultUtil.build();
        PageHelper.startPage(dto.getPageNumber(), dto.getPageSize());
        List<OaBill> list = oaBillMapper.list(dto);
        if(list.size() > 0){
            Page<OaBill> page = (Page<OaBill>) list;
            result.rows(page.getResult());
            result.total(page.getTotal());
        }
        return result.getResult();
    }

    @GetMapping("/pending")
    public List<OaBillPending> pending(OaBill dto){
        if(dto.getStopFlag() == null){
            dto.setStopFlag((byte) 2);
        }
        List<OaBill> list = oaBillMapper.list(dto);
        List<OaBillPending> pendingList = new ArrayList<>();
        Code code = new Code();
        code.setCode((short) 1);
        List<Code> codeList = codeMapper.list(code);
        Map<String, String> codeMap = codeList.stream().collect(Collectors.toMap(Code::getCodeNo, Code::getName));
        list.forEach((item) -> {
            String billType = item.getBillType();
            if(codeMap.containsKey(billType)){
                String billTypeName = codeMap.get(billType);
                OaBillPending pending = new OaBillPending();
                pending.setApplyName(item.getApplyName());
                pending.setApplyTime(item.getCreateTime());
                pending.setBillId(item.getBillId());
                pending.setBillType(item.getBillType());
                pending.setBillTypeName(billTypeName);
                pending.setTitle(billTypeName + "申请");
                if((int)dto.getCurrentUserId() == (int)item.getApplyId()){
                    pending.setType(1);
                    pending.setTypeName("我的申请");
                }else {
                    pending.setType(2);
                    pending.setTypeName("我的审批");
                }
                pendingList.add(pending);
            }
        });
        return pendingList;
    }

    /**
     * 订单投递
     */
    @PostMapping("/deliver")
    public Map<String, Object> deliver(OaBill dto) {
        return workFlowEngine.deliver(dto, true);
    }

    @PostMapping("/simulateDeliver")
    public Map<String, Object> simulateDeliver(OaBill dt){
        ResultUtil.Result result = ResultUtil.build();
        List<String> stepList = new ArrayList<>();
        List<String> approveNameList = new ArrayList<>();
        OaBill dto = oaBillMapper.getById(dt.getBillId());
        boolean stopFlag = true;
        if(1 == dto.getStopFlag()){
            stopFlag = false;
        }
        String historyProcessList = dto.getHistoryProcessList();
        if(!StrUtil.isBlank(historyProcessList)){
            String[] processNodes = historyProcessList.split(",");
            for (int i=0; i<processNodes.length; i++){
                String processNode = processNodes[i];
                String[] nodeItem = processNode.split("@");
                stepList.add(nodeItem[0]);
                approveNameList.add(nodeItem[1]);
            }
            result.total(processNodes.length);
        }
        OaBill bill = dto;
        while (stopFlag){
            bill.setPassFlag((byte) 1);
            Map<String, Object> deliver = workFlowEngine.deliver(bill, false);
            Map<String, Object> properties = (Map<String, Object>) deliver.get("properties");
            bill = (OaBill) properties.get("bill");
            if(1 == bill.getStopFlag()){
                stopFlag = false;
            }
            stepList.add((String) properties.get("processDesc"));
            approveNameList.add((String) properties.get("approveName"));
        }
        List<Map<String, Object>> rows = new ArrayList<>();
        // 汇总进度
        for (int i=0; i<stepList.size(); i++){
            Map<String, Object> row = new HashMap<>();
            Integer key = i+1;
            row.put("key", key);
            row.put("title", approveNameList.get(i));
            row.put("desc", stepList.get(i));
            rows.add(row);
        }
        result.rows(rows);
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
    public int approve(OaBill dto){
        return oaBillMapper.approve(dto);
    }

}
