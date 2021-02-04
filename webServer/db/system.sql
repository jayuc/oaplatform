#
# 用户机构权限项目
# 余杰 create by 2021/01/04

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
  `yes_leader` tinyint DEFAULT NULL COMMENT '是否是领导机构,1表示是',
  `leader_id` int(11) DEFAULT NULL COMMENT '负责人id',
  `deputy_id` int(11) DEFAULT NULL COMMENT '分管领导id',
  `address` varchar(255) DEFAULT NULL COMMENT '机构地址',
  `tel` varchar(16) DEFAULT NULL COMMENT '联系方式',
  `sort` tinyint DEFAULT NULL COMMENT '排序',
  PRIMARY KEY (`org_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT = '机构表';

# 数据字典名称表
CREATE TABLE `t_sys_code_type` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `code` smallint NOT NULL COMMENT '字段编号',
  `name` varchar(16) DEFAULT NULL COMMENT '字段名称',
  `mark` varchar(128) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT = '数据字典名称表';

# 数据字典表
CREATE TABLE `t_sys_code` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `code` smallint NOT NULL COMMENT '字段编号',
  `code_no` varchar(32) NOT NULL COMMENT '值',
  `name` varchar(16) DEFAULT NULL COMMENT '字段名称',
  `enable_flag` tinyint DEFAULT NULL COMMENT '是否启用,1表示启用',
  `mark` varchar(128) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT = '数据字典表';

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
  `org_code` varchar(32) DEFAULT NULL COMMENT '机构编号',
  `role_id` int(11) DEFAULT NULL COMMENT '角色id',
  `tel` varchar(16) DEFAULT NULL COMMENT '联系方式',
  `mobile_tel` varchar(16) DEFAULT NULL COMMENT '手机号',
  `age` int(11) DEFAULT NULL COMMENT '年龄',
  `sex` tinyint DEFAULT NULL COMMENT '性别',
  PRIMARY KEY (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT = '用户表';

# 菜单表
CREATE TABLE `t_sys_menu` (
  `menu_id` int(11) NOT NULL AUTO_INCREMENT,
  `menu_code` varchar(32) UNIQUE DEFAULT NULL COMMENT '菜单编码',
  `parent_code` varchar(32) NOT NULL COMMENT '父菜单编码',
  `menu_name` varchar(32) NOT NULL COMMENT '菜单名称',
  `menu_path` varchar(32) NOT NULL COMMENT '菜单地址',
  `menu_image` varchar(32) DEFAULT NULL COMMENT '菜单图标',
  `level` tinyint NOT NULL COMMENT '菜单层级，一级菜单，二级菜单，三级菜单',
  `sort` tinyint DEFAULT NULL COMMENT '排序',
  `enable_flag` tinyint DEFAULT NULL COMMENT '是否启用',
  `permission_flag` tinyint DEFAULT NULL COMMENT '权限标记，1：所用用户都有此菜单',
  `mark` varchar(256) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`menu_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT = '菜单表';

# 系统功能表
CREATE TABLE `t_sys_function` (
  `function_id` int(11) NOT NULL AUTO_INCREMENT,
  `function_code` varchar(32) UNIQUE DEFAULT NULL COMMENT '系统功能编码',
  `menu_code` varchar(32) DEFAULT NULL COMMENT '菜单编码',
  `function_name` varchar(32) NOT NULL COMMENT '系统功能名称',
  `enable_flag` tinyint DEFAULT NULL COMMENT '是否启用',
  `mark` varchar(256) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`function_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT = '系统功能表';

# 角色权限关系表
CREATE TABLE `t_sys_role_permission` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `role_id` int(11) NOT NULL COMMENT '角色id',
  `function_id` int(11) NOT NULL COMMENT '系统功能id',
  `type` tinyint DEFAULT NULL COMMENT '菜单还是系统功能编码，1表示菜单，2表示系统功能',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT = '角色用户表';

# 角色表
CREATE TABLE `t_sys_role` (
  `role_id` int(11) NOT NULL AUTO_INCREMENT,
  `role_name` varchar(16) DEFAULT NULL COMMENT '登录名',
  `enable_flag` tinyint DEFAULT NULL COMMENT '是否启用',
  `mark` varchar(256) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`role_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT = '角色表';

# 角色用户表
CREATE TABLE `t_sys_role_user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) NOT NULL COMMENT '用户id',
  `role_id` int(11) NOT NULL COMMENT '角色id',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT = '角色用户表';

