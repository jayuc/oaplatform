#
# 2020年12月25日需要执行的数据库脚本
# 余杰 create by 2020/12/25

CREATE database if NOT EXISTS `oa` default character set utf8mb4 collate utf8mb4_unicode_ci;
use `oa`;

SET NAMES utf8mb4;

INSERT t_sys_code (code,code_no,name,enable_flag) VALUE (1,3,'经济业务支出',1);
INSERT t_sys_code (code,code_no,name,enable_flag) VALUE (1,4,'培训申请',1);
INSERT t_sys_code (code,code_no,name,enable_flag) VALUE (1,5,'会议室申请',1);
INSERT t_sys_code (code,code_no,name,enable_flag) VALUE (1,6,'出差交通工具',1);
INSERT t_sys_code (code,code_no,name,enable_flag) VALUE (1,7,'员工因私出国',1);
INSERT t_sys_code (code,code_no,name,enable_flag) VALUE (1,8,'调阅会计档案',1);
INSERT t_sys_code (code,code_no,name,enable_flag) VALUE (1,9,'合理化建议',1);