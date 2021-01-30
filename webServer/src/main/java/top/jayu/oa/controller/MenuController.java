package top.jayu.oa.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.jayu.oa.param.BaseParam;
import top.jayu.oa.service.MenuService;

import java.util.Map;

@RestController
@RequestMapping("/menu")
public class MenuController {

    @Autowired
    MenuService menuService;

    @GetMapping("/getMenuTree")
    public Map<String, Object> getMenuTree(BaseParam dto){
        String upperLoginName = dto.getCurrentLoginName().toUpperCase();
        if("ADMIN".equals(upperLoginName)){
            return menuService.menuTree(null);
        }
        return menuService.menuTree(dto.getCurrentUserId());
    }

}
