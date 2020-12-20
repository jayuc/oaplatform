#
# 余杰 create by 2020/12/11

CREATE database if NOT EXISTS `oa` default character set utf8mb4 collate utf8mb4_unicode_ci;
use `oa`;

SET NAMES utf8mb4;

# 机构表
CREATE TABLE `t_sys_org` (
  `org_id` int(11) NOT NULL AUTO_INCREMENT,
  `parent_id` int(11) COMMENT '父机构主键ID',
  `org_name` varchar(128) DEFAULT NULL COMMENT '机构名',
  `short_org_name` varchar(64) DEFAULT NULL COMMENT '机构简称',
  `org_code` varchar(32) UNIQUE DEFAULT NULL COMMENT '机构编号,可用于机构树生成和机构权限判断',
  `org_code_priv` varchar(32) DEFAULT NULL COMMENT '机构权限编码',
  `yes_office` tinyint DEFAULT NULL COMMENT '是否是机关,1表示是',
  `leader_id` int(11) DEFAULT NULL COMMENT '负责人id',
  `deputy_id` int(11) DEFAULT NULL COMMENT '分管领导id',
  `address` varchar(255) DEFAULT NULL COMMENT '机构地址',
  `tel` varchar(16) DEFAULT NULL COMMENT '联系方式',
  `sort` tinyint DEFAULT NULL COMMENT '排序',
  PRIMARY KEY (`org_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

# 职员表
CREATE TABLE `t_sys_employ` (
  `employ_id` int(11) NOT NULL AUTO_INCREMENT,
  `employ_name` varchar(16) DEFAULT NULL COMMENT '姓名',
  `employ_code` varchar(32) UNIQUE DEFAULT NULL COMMENT '职员编号',
  `position` varchar(32) DEFAULT NULL COMMENT '岗位',
  `work_age` tinyint DEFAULT NULL COMMENT '工龄',
  `yes_chief` tinyint DEFAULT NULL COMMENT '是否是科级干部',
  `org_id` int(11) DEFAULT NULL COMMENT '机构id',
  `org_code` varchar(32) DEFAULT NULL COMMENT '机构编号',
  `tel` varchar(16) DEFAULT NULL COMMENT '联系方式',
  `mobile_tel` varchar(16) DEFAULT NULL COMMENT '手机号',
  `age` int(11) DEFAULT NULL COMMENT '年龄',
  `sex` tinyint DEFAULT NULL COMMENT '性别',
  `address` varchar(255) DEFAULT NULL COMMENT '家庭地址',
  PRIMARY KEY (`employ_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

# 用户表
CREATE TABLE `t_sys_user` (
  `user_id` int(11) NOT NULL AUTO_INCREMENT,
  `login_name` varchar(16) DEFAULT NULL COMMENT '登录名',
  `password` varchar(32) DEFAULT NULL COMMENT '密码',
  `user_name` varchar(16) DEFAULT NULL COMMENT '姓名',
  `user_code` varchar(32) DEFAULT NULL COMMENT '用户编号',
  `employ_id` int(11) DEFAULT NULL COMMENT '职员id',
  `position` varchar(32) DEFAULT NULL COMMENT '岗位',
  `yes_chief` tinyint DEFAULT NULL COMMENT '是否是科级干部',
  `org_id` int(11) DEFAULT NULL COMMENT '机构id',
  `org_code` varchar(32) UNIQUE DEFAULT NULL COMMENT '机构编号',
  `role_id` int(11) DEFAULT NULL COMMENT '角色id',
  `tel` varchar(16) DEFAULT NULL COMMENT '联系方式',
  `mobile_tel` varchar(16) DEFAULT NULL COMMENT '手机号',
  `age` int(11) DEFAULT NULL COMMENT '年龄',
  `sex` tinyint DEFAULT NULL COMMENT '性别',
  PRIMARY KEY (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

# 数据字典名称表
CREATE TABLE `t_sys_code_type` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `code` smallint NOT NULL COMMENT '字段编号',
  `name` varchar(16) DEFAULT NULL COMMENT '字段名称',
  `mark` varchar(128) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

# 数据字典表
CREATE TABLE `t_sys_code` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `code` smallint NOT NULL COMMENT '字段编号',
  `code_no` tinyint NOT NULL COMMENT '值',
  `name` varchar(16) DEFAULT NULL COMMENT '字段名称',
  `enable_flag` tinyint DEFAULT NULL COMMENT '是否启用,1表示启用',
  `mark` varchar(128) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

# 流程单表
CREATE TABLE `t_oa_bill` (
  `bill_id` int(11) NOT NULL AUTO_INCREMENT,
  `bill_code` varchar(32) UNIQUE DEFAULT NULL COMMENT '编号',
  `bill_type` tinyint NOT NULL COMMENT '表单类别,数据字典：1',
  `current_step` varchar(32) NOT NULL COMMENT '表单当前步骤，可以动态调整',
  `stop_flag` tinyint DEFAULT NULL COMMENT '终止标记，1表示终止 2表示未终止',
  `apply_id` int(11) DEFAULT NULL COMMENT '申请人id',
  `next_approve_list` varchar(128) DEFAULT NULL COMMENT '下一步审批人id列表，中间用英文逗号隔开，例如,2,34,',
  `history_approve_list` varchar(256) DEFAULT NULL COMMENT '历史审批人id列表，中间用英文逗号隔开，例如,2,34,',
  `apply_org_id` int(11) DEFAULT NULL COMMENT '申请人机构id',
  `apply_org_code_priv` varchar(32) DEFAULT NULL COMMENT '申请人机构权限编码,用来判断机构层级',
  `start_time` datetime DEFAULT NULL COMMENT '开始日期',
  `end_time` datetime DEFAULT NULL COMMENT '结束日期',
  `work_age` tinyint DEFAULT NULL COMMENT '工龄',
  `holiday_type` tinyint DEFAULT NULL COMMENT '休假标准',
  `days` smallint DEFAULT NULL COMMENT '天数',
  `people_number` smallint DEFAULT NULL COMMENT '人数',
  `content` varchar(1024) DEFAULT NULL COMMENT '内容（比如出差内容）',
  `address` varchar(128) DEFAULT NULL COMMENT '地点',
  `travel_tool` tinyint DEFAULT NULL COMMENT '出行工具',
  `amount` int(11) DEFAULT NULL COMMENT '金额',
  `file_url1` varchar(256) DEFAULT NULL COMMENT '附件1地址',
  `file_url2` varchar(256) DEFAULT NULL COMMENT '附件2地址',
  `file_url3` varchar(256) DEFAULT NULL COMMENT '附件3地址',
  `history_process_list` varchar(256) DEFAULT NULL COMMENT '历史流程节点记录',
  `extend_content` varchar(2048) DEFAULT NULL COMMENT '扩展内容（存放json字符串）',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `mark` varchar(256) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`bill_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

# 流程步骤表
CREATE TABLE `t_oa_process` (
  `process_id` int(11) NOT NULL AUTO_INCREMENT,
  `bill_type` tinyint NOT NULL COMMENT '表单类别,数据字典：1',
  `current_step` varchar(32) DEFAULT NULL COMMENT '当前步骤',
  `next_step` varchar(32) DEFAULT NULL COMMENT '下一步骤名称',
  `next_approve_function_id` int(11) DEFAULT NULL COMMENT '下一步审批人查询方法id',
  `parent_step` varchar(32) DEFAULT NULL COMMENT '父步骤',
  `process_condition_id` int(11) DEFAULT NULL COMMENT '流程条件id',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `process_desc` varchar(32) DEFAULT NULL COMMENT '流程描述',
  `mark` varchar(128) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`process_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

# 流程条件
CREATE TABLE `t_oa_process_condition` (
  `process_condition_id` int(11) NOT NULL AUTO_INCREMENT,
  `input` varchar(32) NOT NULL COMMENT '条件输入,例如：userId',
  `input_value_type` varchar(32) NOT NULL COMMENT '条件输入值类型,例如：String,Integer',
  `ioc_entity_name` varchar(32) NOT NULL COMMENT 'spring容器中实体名字',
  `ioc_entity_method` varchar(32) NOT NULL COMMENT '方法',
  `success_to` varchar(32) NOT NULL COMMENT '结果为true流向步骤名',
  `success_condition_id` int(11) DEFAULT NULL COMMENT '流程条件id,结果为true后,如果不为空，则继续进入此条件',
  `success_approve_function_id` int(11) DEFAULT NULL COMMENT '查询审批人方法id,结果为true时通过此方法查询审批人',
  `fail_to` varchar(32) NOT NULL COMMENT '结果为false的流向的步骤名',
  `fail_condition_id` int(11) DEFAULT NULL COMMENT '流程条件id,结果为false后,如果不为空，则继续进入此条件',
  `fail_approve_function_id` int(11) DEFAULT NULL COMMENT '查询审批人方法id,结果为false时通过此方法查询审批人',
  `condition_desc` varchar(1024) DEFAULT NULL COMMENT '条件描述',
  PRIMARY KEY (`process_condition_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

# 流程方法
CREATE TABLE `t_oa_process_function` (
  `process_function_id` int(11) NOT NULL AUTO_INCREMENT,
  `input` varchar(32) NOT NULL COMMENT '条件输入,例如：userId',
  `input_value_type` varchar(32) NOT NULL COMMENT '条件输入值类型,例如：String,Integer',
  `ioc_entity_name` varchar(32) NOT NULL COMMENT 'spring容器中实体名字',
  `ioc_entity_method` varchar(32) NOT NULL COMMENT '方法',
  `function_name` varchar(32) DEFAULT NULL COMMENT '方法名，例如部门负责人',
  `function_desc` varchar(1024) DEFAULT NULL COMMENT '条件描述',
  PRIMARY KEY (`process_function_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

# 流程单操作记录
CREATE TABLE `t_oa_bill_opera` (
  `bill_opera_id` int(11) NOT NULL AUTO_INCREMENT,
  `bill_code` varchar(32) DEFAULT NULL COMMENT '编号',
  `bill_type` tinyint NOT NULL COMMENT '表单类别,数据字典：1',
  `bill_step` varchar(32) NOT NULL COMMENT '流程的步骤',
  `opera_id` int(11) DEFAULT NULL COMMENT '操作人id',
  `pass_flag` tinyint NOT NULL COMMENT '是否同意 1同意，2不同意，0创建申请单',
  `content` varchar(1024) DEFAULT NULL COMMENT '审批意见',
  `create_time` datetime DEFAULT NULL COMMENT '审批时间',
  PRIMARY KEY (`bill_opera_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;


# 模拟数据
INSERT t_oa_process_function (input,input_value_type,ioc_entity_name,ioc_entity_method,function_name)
VALUE ('applyOrgId', 'Integer', 'orgService', 'findOrgLeader', '部门负责人');
INSERT t_oa_process_function (input,input_value_type,ioc_entity_name,ioc_entity_method,function_name)
VALUE ('applyOrgCodePriv', 'String', 'orgService', 'findCompanyDeputy', '市局分管领导');
INSERT t_oa_process_function (input,input_value_type,ioc_entity_name,ioc_entity_method,function_name)
VALUE ('applyOrgCodePriv', 'String', 'orgService', 'findCompanyLeader', '市局负责人');
INSERT t_oa_process_function (input,input_value_type,ioc_entity_name,ioc_entity_method,function_name)
VALUE ('applyOrgCodePriv', 'String', 'orgService', 'findUnitDeputy', '单位分管领导');
INSERT t_oa_process_function (input,input_value_type,ioc_entity_name,ioc_entity_method,function_name)
VALUE ('applyOrgCodePriv', 'String', 'orgService', 'findUnitLeader', '单位负责人');

INSERT t_oa_process_condition (input,input_value_type,ioc_entity_name,ioc_entity_method,success_to,fail_to,condition_desc,
success_approve_function_id,fail_approve_function_id, success_condition_id, fail_condition_id)
VALUE ('applyOrgId', 'Integer', 'orgService', 'orgIfOffice', '00', '01', '是否是机关', 1, 1, 4, 4);
INSERT t_oa_process_condition (input,input_value_type,ioc_entity_name,ioc_entity_method,success_to,fail_to,condition_desc,success_approve_function_id)
VALUE ('applyId', 'Integer', 'userService', 'userIfChief', '00', 'end', '是否是科级干部',2);
INSERT t_oa_process_condition (input,input_value_type,ioc_entity_name,ioc_entity_method,success_to,fail_to,condition_desc,success_approve_function_id)
VALUE ('applyId', 'Integer', 'userService', 'userIfLeader', '00', 'end', '是否是部门/单位负责人',3);
INSERT t_oa_process_condition (input,input_value_type,ioc_entity_name,ioc_entity_method,success_to,fail_to,condition_desc,
success_approve_function_id, fail_approve_function_id, success_condition_id)
VALUE ('applyId', 'Integer', 'userService', 'userIfLeader', '0000', '00', '是否是部门负责人',2, 1, 6);
INSERT t_oa_process_condition (input,input_value_type,ioc_entity_name,ioc_entity_method,success_to,fail_to,condition_desc)
VALUE ('applyId', 'Integer', 'userService', 'userIfCompanyLeader', 'end', 'end', '是否是市局负责人');
INSERT t_oa_process_condition (input,input_value_type,ioc_entity_name,ioc_entity_method,success_to,fail_to,condition_desc,
fail_approve_function_id)
VALUE ('applyId', 'Integer', 'userService', 'userIfCompanyLeader', 'end', '0000', '是否是市局负责人', 2);

INSERT t_oa_process (bill_type,current_step,parent_step,process_condition_id, process_desc)
VALUE (1, '00', 'root', 1, '工单申请');
INSERT t_oa_process (bill_type,current_step,parent_step,process_condition_id, process_desc)
VALUE (1, '0000', '00', 2, '部门负责人审批');
INSERT t_oa_process (bill_type,current_step,parent_step,process_condition_id, process_desc)
VALUE (1, '000000', '0000', 3, '市局分管领导审批');
INSERT t_oa_process (bill_type,current_step,parent_step,next_step, process_desc)
VALUE (1, '00000000', '000000', 'end', '市局负责人审批');
INSERT t_oa_process (bill_type,current_step,parent_step,next_step,next_approve_function_id, process_desc)
VALUE (1, '0100', '01', '010000', 3, '部门负责人审批');
INSERT t_oa_process (bill_type,current_step,parent_step,next_step,next_approve_function_id, process_desc)
VALUE (1, '010000', '0100', '01000000', 4, '单位分管领导审批');
INSERT t_oa_process (bill_type,current_step,parent_step,process_condition_id, process_desc)
VALUE (1, '01000000', '010000', 2, '单位负责人审批');
INSERT t_oa_process (bill_type,current_step,parent_step,process_condition_id, process_desc)
VALUE (1, '0100000000', '01000000', 3, '市局分管领导审批');
INSERT t_oa_process (bill_type,current_step,parent_step,next_step, process_desc)
VALUE (1, '010000000000', '0100000000', 'end', '市局负责人审批');

INSERT t_sys_code_type (code,name) VALUE (1,'流程类型');
INSERT t_sys_code_type (code,name) VALUE (2,'交通工具');
INSERT t_sys_code_type (code,name) VALUE (3,'休假标准');

INSERT t_sys_code (code,code_no,name,enable_flag) VALUE (1,1,'公休假',1);
INSERT t_sys_code (code,code_no,name,enable_flag) VALUE (1,2,'出差',1);

INSERT t_sys_code (code,code_no,name,enable_flag) VALUE (2,1,'火车',1);
INSERT t_sys_code (code,code_no,name,enable_flag) VALUE (2,2,'汽车',1);
INSERT t_sys_code (code,code_no,name,enable_flag) VALUE (2,3,'飞机',1);
INSERT t_sys_code (code,code_no,name,enable_flag) VALUE (2,4,'轮船',1);

INSERT t_sys_code (code,code_no,name,enable_flag) VALUE (3,1,'5天',1);
INSERT t_sys_code (code,code_no,name,enable_flag) VALUE (3,2,'10天',1);
INSERT t_sys_code (code,code_no,name,enable_flag) VALUE (3,3,'15天',1);

INSERT t_sys_user (user_id,user_name,login_name,password,org_id,role_id)
values (9999,'超级管理员','admin','e10adc3949ba59abbe56e057f20f883e',-1,-1);