#
# 余杰 create by 2020/12/11

CREATE database if NOT EXISTS `oa` default character set utf8mb4 collate utf8mb4_unicode_ci;
use `oa`;

SET NAMES utf8mb4;

INSERT t_sys_code_type (code,name) VALUE (1,'流程类型');
INSERT t_sys_code_type (code,name) VALUE (2,'交通工具');
INSERT t_sys_code_type (code,name) VALUE (3,'休假标准');

INSERT t_sys_code (code,code_no,name,enable_flag) VALUE (1,1,'公休假',1);
INSERT t_sys_code (code,code_no,name,enable_flag) VALUE (1,2,'出差',1);
INSERT t_sys_code (code,code_no,name,enable_flag) VALUE (1,3,'经济业务支出',1);
INSERT t_sys_code (code,code_no,name,enable_flag) VALUE (1,4,'培训申请',1);
INSERT t_sys_code (code,code_no,name,enable_flag) VALUE (1,5,'会议室申请',1);
INSERT t_sys_code (code,code_no,name,enable_flag) VALUE (1,6,'出差交通工具',1);
INSERT t_sys_code (code,code_no,name,enable_flag) VALUE (1,7,'员工因私出国',1);
INSERT t_sys_code (code,code_no,name,enable_flag) VALUE (1,8,'调阅会计档案',1);
INSERT t_sys_code (code,code_no,name,enable_flag) VALUE (1,9,'合理化建议',1);

INSERT t_sys_code (code,code_no,name,enable_flag) VALUE (2,1,'火车',1);
INSERT t_sys_code (code,code_no,name,enable_flag) VALUE (2,2,'汽车',1);
INSERT t_sys_code (code,code_no,name,enable_flag) VALUE (2,3,'飞机',1);
INSERT t_sys_code (code,code_no,name,enable_flag) VALUE (2,4,'轮船',1);
INSERT t_sys_code (code,code_no,name,enable_flag) VALUE (2,5,'自带车',1);

INSERT t_sys_code (code,code_no,name,enable_flag) VALUE (3,1,'5天',1);
INSERT t_sys_code (code,code_no,name,enable_flag) VALUE (3,2,'10天',1);
INSERT t_sys_code (code,code_no,name,enable_flag) VALUE (3,3,'15天',1);