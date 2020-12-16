package top.jayu.oa.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.jayu.oa.entity.OrgTree;
import top.jayu.oa.service.OrgService;

import java.util.List;

/**
 * Created by 余杰 on 2020/12/16 11:50
 */

@RestController
@RequestMapping("/org")
public class OrgController {

    @Autowired
    OrgService orgService;

    @GetMapping("/orgTree")
    public List<OrgTree> orgTree(){
        return orgService.orgTree();
    }

}
