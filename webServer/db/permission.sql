#
# 余杰 create by 2020/12/11

CREATE database if NOT EXISTS `oa` default character set utf8mb4 collate utf8mb4_unicode_ci;
use `oa`;

SET NAMES utf8mb4;


INSERT t_sys_menu (menu_code,parent_code,menu_name,menu_path,menu_image,level,sort,enable_flag)
VALUE ('02','root','组织管理','organization','el-icon-notebook-2',1,19,1);

INSERT t_sys_menu (menu_code,parent_code,menu_name,menu_path,menu_image,level,sort,enable_flag)
VALUE ('0201','02','机构管理','/main/orgManage','',2,1,1);
INSERT t_sys_function (function_code,menu_code,function_name,enable_flag)
VALUE ('020101','0201','机构新增',1);
INSERT t_sys_function (function_code,menu_code,function_name,enable_flag)
VALUE ('020102','0201','机构编辑',1);
INSERT t_sys_function (function_code,menu_code,function_name,enable_flag)
VALUE ('020103','0201','机构删除',1);
INSERT t_sys_function (function_code,menu_code,function_name,enable_flag)
VALUE ('020104','0201','机构查看详情',1);
INSERT t_sys_function (function_code,menu_code,function_name,enable_flag)
VALUE ('020105','0201','机构查询',1);

INSERT t_sys_menu (menu_code,parent_code,menu_name,menu_path,menu_image,level,sort,enable_flag)
VALUE ('0202','02','用户管理','/main/userManage','',2,2,1);
INSERT t_sys_function (function_code,menu_code,function_name,enable_flag)
VALUE ('020201','0202','用户新增',1);
INSERT t_sys_function (function_code,menu_code,function_name,enable_flag)
VALUE ('020202','0202','用户编辑',1);
INSERT t_sys_function (function_code,menu_code,function_name,enable_flag)
VALUE ('020203','0202','用户删除',1);
INSERT t_sys_function (function_code,menu_code,function_name,enable_flag)
VALUE ('020204','0202','用户查看详情',1);
INSERT t_sys_function (function_code,menu_code,function_name,enable_flag)
VALUE ('020205','0202','用户重置密码',1);
INSERT t_sys_function (function_code,menu_code,function_name,enable_flag)
VALUE ('020206','0202','用户查询',1);

INSERT t_sys_menu (menu_code,parent_code,menu_name,menu_path,menu_image,level,sort,enable_flag)
VALUE ('0203','02','角色管理','/main/roleManage','',2,3,1);
INSERT t_sys_function (function_code,menu_code,function_name,enable_flag)
VALUE ('020301','0203','角色新增',1);
INSERT t_sys_function (function_code,menu_code,function_name,enable_flag)
VALUE ('020302','0203','角色编辑',1);
INSERT t_sys_function (function_code,menu_code,function_name,enable_flag)
VALUE ('020303','0203','角色删除',1);
INSERT t_sys_function (function_code,menu_code,function_name,enable_flag)
VALUE ('020304','0203','角色查询',1);





