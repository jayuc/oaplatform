#
# 2020年12月25日需要执行的数据库脚本
# 余杰 create by 2020/12/25

CREATE database if NOT EXISTS `oa` default character set utf8mb4 collate utf8mb4_unicode_ci;
use `oa`;

SET NAMES utf8mb4;

DELETE FROM t_sys_menu WHERE menu_name = '修改密码';
DELETE FROM t_sys_function WHERE function_name = '修改密码';

ALTER TABLE t_oa_bill modify COLUMN `bill_type` varchar(32) NOT NULL;

ALTER TABLE t_oa_process modify COLUMN `bill_type` varchar(32) NOT NULL;

ALTER TABLE t_oa_bill_opera modify COLUMN `bill_type` varchar(32) NOT NULL;

ALTER TABLE t_sys_code modify COLUMN `code_no` varchar(32) NOT NULL;

INSERT t_sys_code_type (code,name) VALUE (9,'自定义流程类型');

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

INSERT INTO t_sys_menu (menu_code,parent_code,menu_name,menu_path,menu_image,level,sort,enable_flag)
VALUE ('0402','04','流程步骤','/main/oaProcessStep','',2,2,1);
INSERT t_sys_function (function_code,menu_code,function_name,enable_flag)
VALUE ('040201','0402','流程步骤查询',1);
INSERT t_sys_function (function_code,menu_code,function_name,enable_flag)
VALUE ('040202','0402','流程步骤新增',1);
INSERT t_sys_function (function_code,menu_code,function_name,enable_flag)
VALUE ('040203','0402','流程步骤编辑',1);
INSERT t_sys_function (function_code,menu_code,function_name,enable_flag)
VALUE ('040204','0402','流程步骤删除',1);

CREATE TABLE `t_oa_all_process` (
  `one_process_id` int(11) NOT NULL AUTO_INCREMENT,
  `bill_type` int NOT NULL COMMENT '动态表单类别，可与表单类别进行动态匹配',
  `process_name` varchar(32) DEFAULT NULL COMMENT '流程名称',
  `process_desc` varchar(256) DEFAULT NULL COMMENT '流程描述',
  `create_person_id` int(11) DEFAULT NULL COMMENT '创建人id，对应的用户id',
  `create_person` varchar(16) DEFAULT NULL COMMENT '创建人',
  `update_person_id` int(11) DEFAULT NULL COMMENT '更新人id，对应的用户id',
  `update_person` varchar(16) DEFAULT NULL COMMENT '更新人',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `mark` varchar(128) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`one_process_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT = '流程表';

# 流程步骤
CREATE TABLE `t_oa_process_step` (
  `step_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '步骤id（主键）',
  `step_name` varchar(32) NOT NULL COMMENT '步骤名称',
  `step_type` varchar(32) NOT NULL COMMENT '步骤类型，1：审批 2：条件 3：结束 4：备案',
  `step_value` int(11) DEFAULT NULL COMMENT '值',
  `org_priv_len` FLOAT DEFAULT NULL COMMENT '机构权限长度',
  `success_to` varchar(32) NOT NULL COMMENT '条件成功去向',
  `fail_to` varchar(32) NOT NULL COMMENT '条件失败去向',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`step_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT = '流程步骤';

INSERT t_sys_code_type (code,name) VALUE (7,'流程步骤类型');
INSERT t_sys_code_type (code,name) VALUE (8,'流程字段');

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

CREATE TABLE `t_oa_bill_customer_field` (
  `customer_field_id` int(11) NOT NULL AUTO_INCREMENT,
  `bill_type` int NOT NULL COMMENT '动态表单类别，可与表单类别进行动态匹配',
  `feild_name` varchar(64) DEFAULT NULL COMMENT '字段名称',
  `feild_type` varchar(32) DEFAULT NULL COMMENT '字段类型，数据字典：8',
  `feild_value_type` varchar(32) DEFAULT NULL COMMENT '字段值类型，',
  `feild_value_value` varchar(32) DEFAULT NULL COMMENT '字段值类型的值',
  `create_time` datetime DEFAULT NULL COMMENT '审批时间',
  PRIMARY KEY (`customer_field_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT = '自定义流程字段';
