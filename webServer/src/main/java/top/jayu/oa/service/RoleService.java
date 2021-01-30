package top.jayu.oa.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.jayu.oa.entity.Function;
import top.jayu.oa.entity.Menu;
import top.jayu.oa.entity.MenuFunctionTree;
import top.jayu.oa.mapper.FunctionMapper;
import top.jayu.oa.mapper.MenuMapper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class RoleService {

    @Autowired
    MenuMapper menuMapper;
    @Autowired
    FunctionMapper functionMapper;

    public List<MenuFunctionTree> menuFunctionTree(){
        List<MenuFunctionTree> list = new ArrayList<>();
        List<Menu> level1 = getByLevel(1);
        List<Menu> level2 = getByLevel(2);
        List<Function> functionList = functionMapper.list(new Function());
        Map<String, MenuFunctionTree> menuMap = new HashMap<>();
        level1.forEach((item) -> {
            MenuFunctionTree tree = new MenuFunctionTree(item.getMenuId(), item.getMenuCode(), item.getMenuName());
            list.add(tree);
            menuMap.put(item.getMenuCode(), tree);
        });
        level2.forEach((item) -> {
            if(menuMap.containsKey(item.getParentCode())){
                MenuFunctionTree parentNode = menuMap.get(item.getParentCode());
                MenuFunctionTree tree = new MenuFunctionTree(item.getMenuId(), item.getMenuCode(), item.getMenuName());
                parentNode.getChildren().add(tree);
                menuMap.put(item.getMenuCode(), tree);
            }
        });
        functionList.forEach((item) -> {
            if(menuMap.containsKey(item.getMenuCode())){
                MenuFunctionTree parentNode = menuMap.get(item.getMenuCode());
                MenuFunctionTree tree = new MenuFunctionTree(item.getFunctionId(), item.getFunctionCode(), item.getFunctionName());
                parentNode.getChildren().add(tree);
            }
        });
        return list;
    }

    private List<Menu> getByLevel(int level){
        Menu menu = new Menu();
        menu.setLevel((byte) level);
        return menuMapper.list(menu);
    }

}
