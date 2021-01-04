#
# 2020年12月25日需要执行的数据库脚本
# 余杰 create by 2020/12/25

CREATE database if NOT EXISTS `oa` default character set utf8mb4 collate utf8mb4_unicode_ci;
use `oa`;

SET NAMES utf8mb4;

CREATE TABLE `t_sys_role` (
  `role_id` int(11) NOT NULL AUTO_INCREMENT,
  `role_name` varchar(16) DEFAULT NULL COMMENT '登录名',
  `enable_flag` tinyint DEFAULT NULL COMMENT '是否启用',
  `mark` varchar(256) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`role_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT = '角色表';