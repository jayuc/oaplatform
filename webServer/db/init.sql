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
  `org_code` varchar(32) UNIQUE DEFAULT NULL COMMENT '机构编号',
  `org_code_priv` varchar(32) DEFAULT NULL COMMENT '机构权限编码',
  `yes_office` tinyint DEFAULT NULL COMMENT '是否是机关',
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
  `position` varchar(32) DEFAULT NULL COMMENT '岗位',
  `work_age` tinyint DEFAULT NULL COMMENT '工龄',
  `yes_chief` tinyint DEFAULT NULL COMMENT '是否是科级干部',
  `org_id` int(11) DEFAULT NULL COMMENT '机构id',
  `tel` varchar(16) DEFAULT NULL COMMENT '联系方式',
  `mobile_tel` varchar(16) DEFAULT NULL COMMENT '手机号',
  `age` int(11) DEFAULT NULL COMMENT '年龄',
  `address` varchar(255) DEFAULT NULL COMMENT '家庭地址',
  PRIMARY KEY (`employ_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

# 用户表
CREATE TABLE `t_sys_user` (
  `user_id` int(11) NOT NULL AUTO_INCREMENT,
  `login_name` varchar(16) DEFAULT NULL COMMENT '登录名',
  `password` varchar(32) DEFAULT NULL COMMENT '密码',
  `user_name` varchar(16) DEFAULT NULL COMMENT '姓名',
  `employ_id` int(11) DEFAULT NULL COMMENT '职员id',
  `yes_chief` tinyint DEFAULT NULL COMMENT '是否是科级干部',
  `org_id` int(11) DEFAULT NULL COMMENT '机构id',
  `role_id` int(11) DEFAULT NULL COMMENT '角色id',
  `tel` varchar(16) DEFAULT NULL COMMENT '联系方式',
  `mobile_tel` varchar(16) DEFAULT NULL COMMENT '手机号',
  `age` int(11) DEFAULT NULL COMMENT '年龄',
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

# 流程表
CREATE TABLE `t_oa_process` (
  `process_id` int(11) NOT NULL AUTO_INCREMENT,
  `process_type` smallint NOT NULL COMMENT '表单类别',
  `prev_step` varchar(32) NOT NULL COMMENT '表单步骤，start为第一个步骤',
  `next_step` varchar(32) NOT NULL COMMENT '表单步骤，end为最后一个步骤',
  `approve_org_id` int(11) DEFAULT NULL COMMENT '审批机构',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `mark` varchar(128) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`process_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

# 流程单表
CREATE TABLE `t_oa_bill` (
  `bill_id` int(11) NOT NULL AUTO_INCREMENT,
  `bill_code` varchar(32) UNIQUE DEFAULT NULL COMMENT '编号',
  `bill_type` tinyint NOT NULL COMMENT '表单类别,数据字典：1',
  `current_step` varchar(32) NOT NULL COMMENT '表单当前步骤，可以动态调整',
  `stop_flag` tinyint DEFAULT NULL COMMENT '终止标记，1表示终止 2表示未终止',
  `employ_id` int(11) DEFAULT NULL COMMENT '申请人id',
  `next_employ_id` int(11) DEFAULT NULL COMMENT '审批人',
  `next_org_id` int(11) DEFAULT NULL COMMENT '审批机构',
  `org_id` int(11) DEFAULT NULL COMMENT '机构id',
  `org_oa_type` tinyint DEFAULT NULL COMMENT '机构流程类型',
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
  `extend_content` varchar(2048) DEFAULT NULL COMMENT '扩展内容（存放json字符串）',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `mark` varchar(256) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`bill_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

# 流程单操作记录
CREATE TABLE `t_oa_bill_opera` (
  `bill_opera_id` int(11) NOT NULL AUTO_INCREMENT,
  `bill_id` int(11) NOT NULL COMMENT '流程单id',
  `bill_step` varchar(32) NOT NULL COMMENT '流程的步骤',
  `approve_employ_id` int(11) DEFAULT NULL COMMENT '审批人',
  `approve_org_id` int(11) DEFAULT NULL COMMENT '审批机构',
  `pass_flag` tinyint NOT NULL COMMENT '是否同意 1同意，2不同意',
  `content` varchar(1024) DEFAULT NULL COMMENT '审批意见',
  `create_time` datetime DEFAULT NULL COMMENT '审批时间',
  PRIMARY KEY (`bill_opera_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;


# 模拟数据
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

INSERT t_sys_user (user_id,user_name,login_name,password,org_id,role_id,tel,age)
values (-1,'超级管理员','admin','e10adc3949ba59abbe56e057f20f883e',2,1,'18823747865',32);