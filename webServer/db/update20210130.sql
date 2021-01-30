#
# 2020年12月25日需要执行的数据库脚本
# 余杰 create by 2020/12/25

CREATE database if NOT EXISTS `oa` default character set utf8mb4 collate utf8mb4_unicode_ci;
use `oa`;

SET NAMES utf8mb4;

ALTER TABLE t_sys_function ADD COLUMN `menu_code` varchar(32) DEFAULT NULL COMMENT '菜单编码';

ALTER TABLE t_sys_role_permission ALTER COLUMN `type` set default 1;

