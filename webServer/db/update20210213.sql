#
# 2020年12月25日需要执行的数据库脚本
# 余杰 create by 2020/12/25

CREATE database if NOT EXISTS `oa` default character set utf8mb4 collate utf8mb4_unicode_ci;
use `oa`;

SET NAMES utf8mb4;

DELETE FROM t_sys_menu WHERE menu_name = '修改密码';
DELETE FROM t_sys_function WHERE function_name = '修改密码';
