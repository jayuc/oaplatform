#
# 2020年12月25日需要执行的数据库脚本
# 余杰 create by 2020/12/25

CREATE database if NOT EXISTS `oa` default character set utf8mb4 collate utf8mb4_unicode_ci;
use `oa`;

SET NAMES utf8mb4;

INSERT t_sys_code (code,code_no,name,enable_flag) VALUE (2,5,'自带车',1);

ALTER TABLE t_oa_process ADD COLUMN `org_priv_len` tinyint NOT NULL;

DELETE FROM t_oa_process;

ALTER TABLE t_sys_org ADD COLUMN `yes_leader` tinyint DEFAULT NULL;

update t_sys_org set yes_leader = 1 where org_name like '%领导';

ALTER TABLE t_oa_bill ADD COLUMN `approve_org_code_priv` varchar(32) DEFAULT NULL;

ALTER TABLE t_oa_bill ADD COLUMN `pass_flag` tinyint DEFAULT NULL;

ALTER TABLE t_oa_bill ADD COLUMN `approve_id` int(11) DEFAULT NULL;

ALTER TABLE t_oa_bill ADD COLUMN `approve_content` varchar(256) DEFAULT NULL;

ALTER TABLE t_oa_bill ADD COLUMN `approve_name` varchar(16) DEFAULT NULL;