#
# 2020年12月25日需要执行的数据库脚本
# 余杰 create by 2020/12/25

CREATE database if NOT EXISTS `oa` default character set utf8mb4 collate utf8mb4_unicode_ci;
use `oa`;

SET NAMES utf8mb4;

DELETE FROM t_sys_menu WHERE menu_name = '修改密码';
DELETE FROM t_sys_function WHERE function_name = '修改密码';

ALTER TABLE t_oa_bill modify COLUMN `bill_type` int NOT NULL;

ALTER TABLE t_oa_process modify COLUMN `bill_type` int NOT NULL;

ALTER TABLE t_oa_bill_opera modify COLUMN `bill_type` int NOT NULL;

INSERT t_sys_menu (menu_code,parent_code,menu_name,menu_path,menu_image,level,sort,enable_flag)
VALUE ('04','root','自定义流程','customeprocess','el-icon-copy-document',1,3,1);

INSERT t_sys_menu (menu_code,parent_code,menu_name,menu_path,menu_image,level,sort,enable_flag)
VALUE ('0401','04','自定义流程','/main/createProcess','',2,1,1);
INSERT t_sys_function (function_code,menu_code,function_name,enable_flag)
VALUE ('040102','0401','自定义流程新增',1);
INSERT t_sys_function (function_code,menu_code,function_name,enable_flag)
VALUE ('040103','0401','自定义流程编辑',1);
INSERT t_sys_function (function_code,menu_code,function_name,enable_flag)
VALUE ('040104','0401','自定义流程删除',1);
