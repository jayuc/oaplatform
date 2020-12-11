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
  `code` varchar(32) DEFAULT NULL COMMENT '机构编号',
  `code_priv` varchar(32) DEFAULT NULL COMMENT '机构权限编码',
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
  `position` varchar(32) DEFAULT NULL COMMENT '职位',
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


# 模拟数据
INSERT t_sys_org (id,parent_id,name,code,address) VALUE (1,0,'交通支队','0110','临安区人民路83号');
INSERT t_sys_org (id,parent_id,name,code,address) VALUE (2,1,'交通一大队','011001','临安区抢滩路56号');
INSERT t_sys_org (id,parent_id,name,code,address) VALUE (3,1,'交通二大队','011002','临安区繁华大道1号');
INSERT t_sys_org (id,parent_id,name,code,address) VALUE (4,1,'交通三大队','011003','临安区东都大道21号');

INSERT t_sys_user (name,org_id,role_id,tel,age) values ('刘军',2,1,'18823747865',32);
INSERT t_sys_user (name,org_id,role_id,tel,age) values ('张小东',3,1,'18323744864',22);
INSERT t_sys_user (name,org_id,role_id,tel,age) values ('石前',4,1,'18523447845',25);