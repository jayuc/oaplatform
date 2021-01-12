#
# 2020年12月25日需要执行的数据库脚本
# 余杰 create by 2020/12/25

CREATE database if NOT EXISTS `oa` default character set utf8mb4 collate utf8mb4_unicode_ci;
use `oa`;

SET NAMES utf8mb4;

update t_sys_org set org_code = '340007060000',org_code_priv = '34000706',sort = 6,parent_id = 8 where org_id = 79;
update t_sys_org set org_code = '340007060100',org_code_priv = '3400070601' where org_id = 121;
update t_sys_org set org_code = '340007060200',org_code_priv = '3400070602' where org_id = 122;
update t_sys_org set org_code = '340007060300',org_code_priv = '3400070603' where org_id = 123;
update t_sys_org set org_code = '340007060400',org_code_priv = '3400070604' where org_id = 124;
update t_sys_org set org_code = '340007060500',org_code_priv = '3400070605' where org_id = 125;

