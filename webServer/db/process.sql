#
# 2020年12月25日需要执行的数据库脚本
# 余杰 create by 2020/12/25

CREATE database if NOT EXISTS `oa` default character set utf8mb4 collate utf8mb4_unicode_ci;
use `oa`;

SET NAMES utf8mb4;

# 查找上级部门负责人
INSERT t_oa_process_function (process_function_id,input,input_value_type,ioc_entity_name,ioc_entity_method,function_name)
VALUE (8, 'OaBill', 'OaBill', 'oaBillService', 'findUpOrgLeader', '上级部门负责人');

# 出差
# 循环查询部门负责人条件
INSERT t_oa_process_condition (process_condition_id, input,input_value_type,ioc_entity_name,ioc_entity_method,
success_to,fail_to,condition_desc,success_approve_function_id,fail_approve_function_id)
VALUE (8, 'OaBill', 'OaBill', 'oaBillService', 'loopOrgLeader', '000000', 'loopOrgLeader_0000',
'是否跳出部门负责人循环', 6, 8);

# 出差
INSERT t_oa_process (process_id, bill_type,current_step,parent_step,next_step,next_approve_function_id,process_desc,org_priv_len)
VALUE (1, 2, '00', 'root', 'loopOrgLeader_0000', 6, '工单申请', 0);
INSERT t_oa_process (process_id, bill_type,current_step,parent_step,process_condition_id,process_desc,org_priv_len)
VALUE (2, 2, 'loopOrgLeader_0000', '00', 8, '上级部门负责人审批', 7);
INSERT t_oa_process (process_id, bill_type,current_step,parent_step,process_condition_id,process_desc,org_priv_len)
VALUE (3, 2, '000000', '00', 7, '单位/部门负责人审批', 3);