#
# 余杰 create by 2020/12/11

CREATE database if NOT EXISTS `oa` default character set utf8mb4 collate utf8mb4_unicode_ci;
use `oa`;

SET NAMES utf8mb4;

INSERT t_sys_code (code,code_no,name,enable_flag) VALUE (2,5,'自带车',1);

ALTER TABLE t_oa_process ADD COLUMN `org_priv_len` tinyint NOT NULL;