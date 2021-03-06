#
# 余杰 create by 2020/12/11

CREATE database if NOT EXISTS `oa` default character set utf8mb4 collate utf8mb4_unicode_ci;
use `oa`;

SET NAMES utf8mb4;

INSERT t_sys_code_type (code,name) VALUE (1,'流程类型');
INSERT t_sys_code_type (code,name) VALUE (2,'交通工具');
INSERT t_sys_code_type (code,name) VALUE (3,'休假标准');
INSERT t_sys_code_type (code,name) VALUE (4,'性别');
INSERT t_sys_code_type (code,name) VALUE (5,'业务域');
INSERT t_sys_code_type (code,name) VALUE (6,'会场类型');
INSERT t_sys_code_type (code,name) VALUE (7,'流程步骤类型');
INSERT t_sys_code_type (code,name) VALUE (8,'流程字段');
INSERT t_sys_code_type (code,name) VALUE (9,'自定义流程类型');

INSERT t_sys_code (code,code_no,name,enable_flag) VALUE (1,1,'公休假',1);
INSERT t_sys_code (code,code_no,name,enable_flag) VALUE (1,2,'出差',1);
INSERT t_sys_code (code,code_no,name,enable_flag) VALUE (1,3,'经济业务支出',1);
INSERT t_sys_code (code,code_no,name,enable_flag) VALUE (1,4,'培训申请',1);
INSERT t_sys_code (code,code_no,name,enable_flag) VALUE (1,5,'会议室申请',1);
INSERT t_sys_code (code,code_no,name,enable_flag) VALUE (1,6,'出差交通工具',1);
INSERT t_sys_code (code,code_no,name,enable_flag) VALUE (1,7,'员工因私出国',1);
INSERT t_sys_code (code,code_no,name,enable_flag) VALUE (1,8,'调阅会计档案',1);
INSERT t_sys_code (code,code_no,name,enable_flag) VALUE (1,9,'合理化建议',1);
INSERT t_sys_code (code,code_no,name,enable_flag) VALUE (1,10,'市局（公司）介绍信开具',1);
INSERT t_sys_code (code,code_no,name,enable_flag) VALUE (1,11,'市局（公司）证件（复印件）使用',1);

INSERT t_sys_code (code,code_no,name,enable_flag) VALUE (2,1,'火车',1);
INSERT t_sys_code (code,code_no,name,enable_flag) VALUE (2,2,'汽车',1);
INSERT t_sys_code (code,code_no,name,enable_flag) VALUE (2,3,'飞机',1);
INSERT t_sys_code (code,code_no,name,enable_flag) VALUE (2,4,'轮船',1);
INSERT t_sys_code (code,code_no,name,enable_flag) VALUE (2,5,'自带车',1);

INSERT t_sys_code (code,code_no,name,enable_flag) VALUE (3,1,'5天',1);
INSERT t_sys_code (code,code_no,name,enable_flag) VALUE (3,2,'10天',1);
INSERT t_sys_code (code,code_no,name,enable_flag) VALUE (3,3,'15天',1);

INSERT t_sys_code (code,code_no,name,enable_flag) VALUE (4,1,'男',1);
INSERT t_sys_code (code,code_no,name,enable_flag) VALUE (4,2,'女',1);

INSERT t_sys_code (code,code_no,name,enable_flag) VALUE (5,1,'办公室',1);
INSERT t_sys_code (code,code_no,name,enable_flag) VALUE (5,2,'安全管理',1);
INSERT t_sys_code (code,code_no,name,enable_flag) VALUE (5,3,'审计',1);
INSERT t_sys_code (code,code_no,name,enable_flag) VALUE (5,4,'人力资源',1);
INSERT t_sys_code (code,code_no,name,enable_flag) VALUE (5,5,'企业管理',1);
INSERT t_sys_code (code,code_no,name,enable_flag) VALUE (5,6,'专卖监督',1);
INSERT t_sys_code (code,code_no,name,enable_flag) VALUE (5,7,'营销',1);
INSERT t_sys_code (code,code_no,name,enable_flag) VALUE (5,8,'财务',1);
INSERT t_sys_code (code,code_no,name,enable_flag) VALUE (5,9,'信息中心',1);
INSERT t_sys_code (code,code_no,name,enable_flag) VALUE (5,10,'纪检监察',1);
INSERT t_sys_code (code,code_no,name,enable_flag) VALUE (5,11,'法规',1);
INSERT t_sys_code (code,code_no,name,enable_flag) VALUE (5,12,'党建群团',1);
INSERT t_sys_code (code,code_no,name,enable_flag) VALUE (5,13,'物流',1);

INSERT t_sys_code (code,code_no,name,enable_flag) VALUE (6,1,'2302会议室',1);
INSERT t_sys_code (code,code_no,name,enable_flag) VALUE (6,2,'2208会议室',1);
INSERT t_sys_code (code,code_no,name,enable_flag) VALUE (6,3,'2207会议室',1);
INSERT t_sys_code (code,code_no,name,enable_flag) VALUE (6,4,'2107会议室',1);

INSERT t_sys_code (code,code_no,name,enable_flag) VALUE (7,'1','审批',1);
INSERT t_sys_code (code,code_no,name,enable_flag) VALUE (7,'2','条件',1);
INSERT t_sys_code (code,code_no,name,enable_flag) VALUE (7,'3','结束',1);
INSERT t_sys_code (code,code_no,name,enable_flag) VALUE (7,'4','备案',1);

INSERT t_sys_code (code,code_no,name,enable_flag) VALUE (8,'1','开始时间',1);
INSERT t_sys_code (code,code_no,name,enable_flag) VALUE (8,'2','结束时间',1);
INSERT t_sys_code (code,code_no,name,enable_flag) VALUE (8,'3','工龄',1);
INSERT t_sys_code (code,code_no,name,enable_flag) VALUE (8,'4','天数',1);
INSERT t_sys_code (code,code_no,name,enable_flag) VALUE (8,'5','人数',1);
INSERT t_sys_code (code,code_no,name,enable_flag) VALUE (8,'6','内容',1);
INSERT t_sys_code (code,code_no,name,enable_flag) VALUE (8,'7','地点',1);
INSERT t_sys_code (code,code_no,name,enable_flag) VALUE (8,'8','出行工具',1);
INSERT t_sys_code (code,code_no,name,enable_flag) VALUE (8,'9','金额',1);
INSERT t_sys_code (code,code_no,name,enable_flag) VALUE (8,'10','备注',1);
INSERT t_sys_code (code,code_no,name,enable_flag) VALUE (8,'11','人员名单',1);