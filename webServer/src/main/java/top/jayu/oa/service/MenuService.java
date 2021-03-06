package top.jayu.oa.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.jayu.oa.entity.Menu;
import top.jayu.oa.entity.MenuFunctionTree;
import top.jayu.oa.entity.RolePermission;
import top.jayu.oa.mapper.MenuMapper;
import top.jayu.oa.mapper.RolePermissionMapper;

import java.util.*;

@Service
public class MenuService {

    @Autowired
    MenuMapper menuMapper;
    @Autowired
    RolePermissionMapper rolePermissionMapper;

    public Map<String, Object> menuTree(Integer userId){
        List<MenuFunctionTree> list = new ArrayList<>();
        Map<String, Object> map = new HashMap<>();
        List<Menu> level1 = getByLevel(1);
        List<Menu> level2 = getByLevel(2);
        Map<String, MenuFunctionTree> menuMap = new HashMap<>();
        Map<String, MenuFunctionTree> mmenuMap = new HashMap<>();
        level1.forEach((item) -> {
            MenuFunctionTree tree = new MenuFunctionTree(item.getMenuId(), item.getMenuCode(), item.getMenuName());
            tree.setAttribute(item);
            list.add(tree);
            menuMap.put(item.getMenuCode(), tree);
            MenuFunctionTree tree2 = new MenuFunctionTree(item.getMenuId(), item.getMenuCode(), item.getMenuName());
            tree2.setAttribute(item);
            mmenuMap.put(item.getMenuCode(), tree2);
        });
        level2.forEach((item) -> {
            if(menuMap.containsKey(item.getParentCode())){
                MenuFunctionTree parentNode = menuMap.get(item.getParentCode());
                MenuFunctionTree tree = new MenuFunctionTree(item.getMenuId(), item.getMenuCode(), item.getMenuName());
                tree.setAttribute(item);
                parentNode.getChildren().add(tree);
                menuMap.put(item.getMenuCode(), tree);
                MenuFunctionTree tree2 = new MenuFunctionTree(item.getMenuId(), item.getMenuCode(), item.getMenuName());
                tree2.setAttribute(item);
                mmenuMap.put(item.getMenuCode(), tree2);
            }
        });
        if(userId != null){
            List<RolePermission> permissions = rolePermissionMapper.getPermissionByUserId(userId);
            handlePermissionMenu(permissions);
            List<MenuFunctionTree> uplist = new ArrayList<>();
            Map<String, MenuFunctionTree> upmenuMap = new HashMap<>();
            Map<String, RolePermission> permissionMap = new HashMap<>();
            permissions.forEach((permission) -> {
                String functionCode = permission.getFunctionCode();
                String l1 = functionCode.substring(0, 2);
                String l2 = functionCode.substring(0, 4);
                MenuFunctionTree l1m = mmenuMap.get(l1);
                MenuFunctionTree l2m = mmenuMap.get(l2);
                if(!upmenuMap.containsKey(l1)){
                    uplist.add(l1m);
                    upmenuMap.put(l1, l1m);
                    if(!upmenuMap.containsKey(l2)){
                        l1m.getChildren().add(l2m);
                        upmenuMap.put(l2, l2m);
                    }
                }else {
                    if(!upmenuMap.containsKey(l2)){
                        l1m.getChildren().add(l2m);
                        upmenuMap.put(l2, l2m);
                    }
                }
                permissionMap.put(functionCode, permission);
            });
            Collections.sort(uplist, new Comparator<MenuFunctionTree>(){
                @Override
                public int compare(MenuFunctionTree o1, MenuFunctionTree o2) {
                    Menu attribute2 = (Menu) o2.getAttribute();
                    Menu attribute1 = (Menu) o1.getAttribute();
                    return attribute1.getSort().compareTo(attribute2.getSort());
                }
            });
            map.put("menu", uplist);
            map.put("permission", permissionMap);
            return map;
        }
        map.put("menu", list);
        map.put("permission", new Menu());
        return map;
    }

    private List<Menu> getByLevel(int level){
        Menu menu = new Menu();
        menu.setLevel((byte) level);
        return menuMapper.list(menu);
    }

    // 获取所有不受权限控制的菜单
    private List<RolePermission> handlePermissionMenu(List<RolePermission> permissions){
        Menu menu = new Menu();
        menu.setPermissionFlag((byte) 1);
        List<Menu> list = menuMapper.list(menu);
        List<RolePermission> result = new ArrayList<>();
        for (Menu m:list){
            if(m.getLevel() == 2){
                RolePermission permission = new RolePermission();
                permission.setFunctionCode(m.getMenuCode() + "00");
                result.add(permission);
                permissions.add(permission);
            }
        }
        return result;
    }

}
