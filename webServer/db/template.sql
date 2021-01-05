#
# 2020年12月25日需要执行的数据库脚本
# 余杰 create by 2020/12/25

CREATE database if NOT EXISTS `oa` default character set utf8mb4 collate utf8mb4_unicode_ci;
use `oa`;

SET NAMES utf8mb4;

UPDATE t_sys_user SET org_id = 32,org_code = '340014020000',position= '党组成员、副局长' WHERE user_id = 71;
UPDATE t_sys_user SET org_id = 80,org_code = '340014060000',position= '副所长' WHERE user_id = 245;
UPDATE t_sys_user SET org_id = 35,org_code = '340014050000',position= '主任' WHERE user_id = 187;
UPDATE t_sys_user SET org_id = 34,org_code = '340014040000',position= '主任' WHERE user_id = 260;