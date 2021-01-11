#
# 2020年12月25日需要执行的数据库脚本
# 余杰 create by 2020/12/25

CREATE database if NOT EXISTS `oa` default character set utf8mb4 collate utf8mb4_unicode_ci;
use `oa`;

SET NAMES utf8mb4;

select * from t_oa_bill_opera where pass_flag = 1 and LENGTH(content) = 0;

update  t_oa_bill_opera set content = '同意' where pass_flag = 1 and LENGTH(content) = 0;

