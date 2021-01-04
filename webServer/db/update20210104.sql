#
# 2020年12月25日需要执行的数据库脚本
# 余杰 create by 2020/12/25

CREATE database if NOT EXISTS `oa` default character set utf8mb4 collate utf8mb4_unicode_ci;
use `oa`;

SET NAMES utf8mb4;

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
  `mark` varchar(256) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`menu_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT = '菜单表';

CREATE TABLE `t_sys_function` (
  `function_id` int(11) NOT NULL AUTO_INCREMENT,
  `function_code` varchar(32) UNIQUE DEFAULT NULL COMMENT '系统功能编码',
  `function_name` varchar(32) NOT NULL COMMENT '系统功能名称',
  `enable_flag` tinyint DEFAULT NULL COMMENT '是否启用',
  `mark` varchar(256) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`function_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT = '系统功能表';

CREATE TABLE `t_sys_role_permission` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `role_id` int(11) NOT NULL COMMENT '角色id',
  `function_id` int(11) NOT NULL COMMENT '系统功能id',
  `type` tinyint NOT NULL COMMENT '菜单还是系统功能编码，1表示菜单，2表示系统功能',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT = '角色用户表';

CREATE TABLE `t_sys_role` (
  `role_id` int(11) NOT NULL AUTO_INCREMENT,
  `role_name` varchar(16) DEFAULT NULL COMMENT '登录名',
  `enable_flag` tinyint DEFAULT NULL COMMENT '是否启用',
  `mark` varchar(256) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`role_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT = '角色表';

CREATE TABLE `t_sys_role_user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) NOT NULL COMMENT '用户id',
  `role_id` int(11) NOT NULL COMMENT '角色id',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT = '角色用户表';