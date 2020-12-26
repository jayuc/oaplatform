#
# 2020年12月25日需要执行的数据库脚本
# 余杰 create by 2020/12/25

CREATE database if NOT EXISTS `oa` default character set utf8mb4 collate utf8mb4_unicode_ci;
use `oa`;

SET NAMES utf8mb4;

# 循环查询部门负责人条件


# 出差
INSERT t_oa_process (process_id, bill_type,current_step,parent_step,next_step,next_approve_function_id,process_desc,org_priv_len)
VALUE (1, 2, '00', 'root', '00', 6, '工单申请',0);
INSERT t_oa_process (process_id, bill_type,current_step,parent_step,process_condition_id,process_desc,org_priv_len)
VALUE (2, 2, '0000', '00', 7, '单位/部门负责人审批', 3);