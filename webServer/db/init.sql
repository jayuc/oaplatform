#
# 余杰 create by 2020/12/11

CREATE database if NOT EXISTS `oa` default character set utf8mb4 collate utf8mb4_unicode_ci;
use `oa`;

SET NAMES utf8mb4;

# 机构表
CREATE TABLE `t_sys_org` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `parent_id` int(11) COMMENT '父机构主键ID',
  `name` varchar(64) DEFAULT NULL COMMENT '机构名',
  `code` varchar(32) UNIQUE DEFAULT NULL COMMENT '机构编号',
  `code_priv` varchar(32) DEFAULT NULL COMMENT '机构权限编码',
  `org_oa_type` tinyint DEFAULT NULL COMMENT '机构流程类型（根据不同机构走不通流程）',
  `address` varchar(255) DEFAULT NULL COMMENT '机构地址',
  `tel` varchar(16) DEFAULT NULL COMMENT '联系方式',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

# 职员表
CREATE TABLE `t_sys_employ` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(16) DEFAULT NULL COMMENT '姓名',
  `login_name` varchar(16) DEFAULT NULL COMMENT '登录名',
  `password` varchar(32) DEFAULT NULL COMMENT '密码',
  `position` varchar(32) DEFAULT NULL COMMENT '岗位',
  `work_age` tinyint DEFAULT NULL COMMENT '工龄',
  `org_id` int(11) DEFAULT NULL COMMENT '机构id',
  `tel` varchar(16) DEFAULT NULL COMMENT '联系方式',
  `mobile_tel` varchar(16) DEFAULT NULL COMMENT '手机号',
  `age` int(11) DEFAULT NULL COMMENT '年龄',
  `address` varchar(255) DEFAULT NULL COMMENT '家庭地址',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

# 用户表
CREATE TABLE `t_sys_user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `login_name` varchar(16) DEFAULT NULL COMMENT '登录名',
  `password` varchar(32) DEFAULT NULL COMMENT '密码',
  `name` varchar(16) DEFAULT NULL COMMENT '姓名',
  `employ_id` int(11) DEFAULT NULL COMMENT '职员id',
  `org_id` int(11) DEFAULT NULL COMMENT '机构id',
  `role_id` int(11) DEFAULT NULL COMMENT '角色id',
  `tel` varchar(16) DEFAULT NULL COMMENT '联系方式',
  `mobile_tel` varchar(16) DEFAULT NULL COMMENT '手机号',
  `age` int(11) DEFAULT NULL COMMENT '年龄',
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
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `code` varchar(32) UNIQUE DEFAULT NULL COMMENT '编号',
  `type` tinyint NOT NULL COMMENT '表单类别,数据字典：1',
  `current_step` varchar(32) NOT NULL COMMENT '表单当前步骤，可以动态调整',
  `stop_flag` tinyint DEFAULT NULL COMMENT '终止标记，1表示终止',
  `employ_id` int(11) DEFAULT NULL COMMENT '申请人id',
  `next_employ_id` int(11) DEFAULT NULL COMMENT '审批人',
  `next_org_id` int(11) DEFAULT NULL COMMENT '审批机构',
  `org_id` int(11) DEFAULT NULL COMMENT '机构id',
  `org_oa_type` tinyint DEFAULT NULL COMMENT '机构流程类型',
  `start_time` datetime DEFAULT NULL COMMENT '开始日期',
  `end_time` datetime DEFAULT NULL COMMENT '结束日期',
  `work_age` tinyint DEFAULT NULL COMMENT '工龄',
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
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

# 流程单操作记录
CREATE TABLE `t_oa_bill_opera` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `bill_id` int(11) NOT NULL COMMENT '流程单id',
  `step` varchar(32) NOT NULL COMMENT '流程的步骤',
  `approve_employ_id` int(11) DEFAULT NULL COMMENT '审批人',
  `approve_org_id` int(11) DEFAULT NULL COMMENT '审批机构',
  `pass_flag` tinyint NOT NULL COMMENT '是否同意 1同意，2不同意',
  `content` varchar(1024) DEFAULT NULL COMMENT '审批意见',
  `create_time` datetime DEFAULT NULL COMMENT '审批时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;


# 模拟数据
INSERT t_sys_code (code,code_no,name,enable_flag) VALUE (1,1,'公休假',1);
INSERT t_sys_code (code,code_no,name,enable_flag) VALUE (1,2,'出差',1);

INSERT t_sys_code (code,code_no,name,enable_flag) VALUE (2,1,'火车',1);
INSERT t_sys_code (code,code_no,name,enable_flag) VALUE (2,2,'汽车',1);
INSERT t_sys_code (code,code_no,name,enable_flag) VALUE (2,3,'飞机',1);
INSERT t_sys_code (code,code_no,name,enable_flag) VALUE (2,4,'轮船',1);

INSERT t_sys_org (id,parent_id,name,code,address) VALUE (1,0,'交通支队','0110','临安区人民路83号');
INSERT t_sys_org (id,parent_id,name,code,address) VALUE (2,1,'交通一大队','011001','临安区抢滩路56号');
INSERT t_sys_org (id,parent_id,name,code,address) VALUE (3,1,'交通二大队','011002','临安区繁华大道1号');
INSERT t_sys_org (id,parent_id,name,code,address) VALUE (4,1,'交通三大队','011003','临安区东都大道21号');

INSERT t_sys_user (name,login_name,password,org_id,role_id,tel,age)
values ('超级管理员','admin','e10adc3949ba59abbe56e057f20f883e',2,1,'18823747865',32);