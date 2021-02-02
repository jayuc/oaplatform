#
# 余杰 create by 2020/12/11

CREATE database if NOT EXISTS `oa` default character set utf8mb4 collate utf8mb4_unicode_ci;
use `oa`;

SET NAMES utf8mb4;

INSERT t_sys_menu (menu_code,parent_code,menu_name,menu_path,menu_image,level,sort,enable_flag)
VALUE ('01','root','流程','process','el-icon-edit',1,2,1);

INSERT t_sys_menu (menu_code,parent_code,menu_name,menu_path,menu_image,level,sort,enable_flag,permission_flag)
VALUE ('0101','01','出差申请','/main/errand','',2,1,1,1);
INSERT t_sys_function (function_code,menu_code,function_name,enable_flag)
VALUE ('010101','0101','出差申请查看',1);
INSERT t_sys_function (function_code,menu_code,function_name,enable_flag)
VALUE ('010102','0101','出差申请删除',1);

INSERT t_sys_menu (menu_code,parent_code,menu_name,menu_path,menu_image,level,sort,enable_flag,permission_flag)
VALUE ('0102','01','年休假申请','/main/leave','',2,2,1,1);
INSERT t_sys_function (function_code,menu_code,function_name,enable_flag)
VALUE ('010201','0102','年休假申请查看',1);
INSERT t_sys_function (function_code,menu_code,function_name,enable_flag)
VALUE ('010202','0102','年休假申请删除',1);

INSERT t_sys_menu (menu_code,parent_code,menu_name,menu_path,menu_image,level,sort,enable_flag,permission_flag)
VALUE ('0103','01','经济业务支出事前申请','/main/cost','',2,3,1,1);
INSERT t_sys_function (function_code,menu_code,function_name,enable_flag)
VALUE ('010301','0103','经济业务支出查看',1);
INSERT t_sys_function (function_code,menu_code,function_name,enable_flag)
VALUE ('010302','0103','经济业务支出删除',1);

INSERT t_sys_menu (menu_code,parent_code,menu_name,menu_path,menu_image,level,sort,enable_flag,permission_flag)
VALUE ('0104','01','培训申请','/main/train','',2,4,1,1);
INSERT t_sys_function (function_code,menu_code,function_name,enable_flag)
VALUE ('010401','0104','培训申请查看',1);
INSERT t_sys_function (function_code,menu_code,function_name,enable_flag)
VALUE ('010402','0104','培训申请删除',1);

INSERT t_sys_menu (menu_code,parent_code,menu_name,menu_path,menu_image,level,sort,enable_flag,permission_flag,mark)
VALUE ('0105','01','机关会议室申请','/main/meeting','',2,5,1,1,'userYesOffice');
INSERT t_sys_function (function_code,menu_code,function_name,enable_flag)
VALUE ('010501','0105','机关会议室申请查看',1);
INSERT t_sys_function (function_code,menu_code,function_name,enable_flag)
VALUE ('010502','0105','机关会议室申请删除',1);

INSERT t_sys_menu (menu_code,parent_code,menu_name,menu_path,menu_image,level,sort,enable_flag,permission_flag)
VALUE ('0106','01','出差交通工具调整申请','/main/travelToolSet','',2,6,1,1);
INSERT t_sys_function (function_code,menu_code,function_name,enable_flag)
VALUE ('010601','0106','交通工具调整查看',1);
INSERT t_sys_function (function_code,menu_code,function_name,enable_flag)
VALUE ('010602','0106','交通工具调整删除',1);

INSERT t_sys_menu (menu_code,parent_code,menu_name,menu_path,menu_image,level,sort,enable_flag,permission_flag)
VALUE ('0107','01','员工因私出国申请','/main/goAbroad','',2,7,2,1);
INSERT t_sys_function (function_code,menu_code,function_name,enable_flag)
VALUE ('010701','0107','员工因私出国查看',1);
INSERT t_sys_function (function_code,menu_code,function_name,enable_flag)
VALUE ('010702','0107','员工因私出国删除',1);

INSERT t_sys_menu (menu_code,parent_code,menu_name,menu_path,menu_image,level,sort,enable_flag,permission_flag)
VALUE ('0108','01','介绍信开具申请','/main/introduce','',2,8,1,1);
INSERT t_sys_function (function_code,menu_code,function_name,enable_flag)
VALUE ('010801','0108','介绍信开具查看',1);
INSERT t_sys_function (function_code,menu_code,function_name,enable_flag)
VALUE ('010802','0108','介绍信开具删除',1);

INSERT t_sys_menu (menu_code,parent_code,menu_name,menu_path,menu_image,level,sort,enable_flag,permission_flag)
VALUE ('0109','01','证件（复印件）使用申请','/main/certificate','',2,9,1,1);
INSERT t_sys_function (function_code,menu_code,function_name,enable_flag)
VALUE ('010901','0109','证件复印件使用查看',1);
INSERT t_sys_function (function_code,menu_code,function_name,enable_flag)
VALUE ('010902','0109','证件复印件使用删除',1);

INSERT t_sys_menu (menu_code,parent_code,menu_name,menu_path,menu_image,level,sort,enable_flag,permission_flag)
VALUE ('0110','01','调阅会计档案申请','/main/lookDoc','',2,10,1,1);
INSERT t_sys_function (function_code,menu_code,function_name,enable_flag)
VALUE ('011001','0110','调阅会计档案查看',1);
INSERT t_sys_function (function_code,menu_code,function_name,enable_flag)
VALUE ('011002','0110','调阅会计档案删除',1);

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

INSERT t_sys_menu (menu_code,parent_code,menu_name,menu_path,menu_image,level,sort,enable_flag)
VALUE ('03','root','系统管理','system','el-icon-setting',1,20,1);

INSERT t_sys_menu (menu_code,parent_code,menu_name,menu_path,menu_image,level,sort,enable_flag)
VALUE ('0302','03','工单操作日志','/main/billOperaLog','',2,1,1);
INSERT t_sys_function (function_code,menu_code,function_name,enable_flag)
VALUE ('030201','0302','工单操作日志查看',1);





