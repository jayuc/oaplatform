#
# 2020年12月25日需要执行的数据库脚本
# 余杰 create by 2020/12/25

CREATE database if NOT EXISTS `oa` default character set utf8mb4 collate utf8mb4_unicode_ci;
use `oa`;

SET NAMES utf8mb4;

# 查找上级部门负责人
INSERT t_oa_process_function (process_function_id,input,input_value_type,ioc_entity_name,ioc_entity_method,function_name)
VALUE (8, 'OaBill', 'OaBill', 'oaBillService', 'findUpOrgLeader', '上级部门负责人');
INSERT t_oa_process_condition (process_condition_id,input,input_value_type,ioc_entity_name,ioc_entity_method,success_to,fail_to,condition_desc)
VALUE (9, 'applyOrgId', 'Integer', 'orgService', 'orgIfOffice', '00', '01', '是否是机关');

# 出差
INSERT t_oa_process (process_id, bill_type,current_step,parent_step,next_step,process_desc,org_priv_len)
VALUE (1, 2, '00', 'root', '00', '工单申请', 0);
INSERT t_oa_process (process_id, bill_type,current_step,parent_step,next_step,next_approve_function_id,process_desc,org_priv_len)
VALUE (2, 2, '0000', '00', '00', 8, '四级部门负责人审批', 7);
INSERT t_oa_process (process_id, bill_type,current_step,parent_step,next_step,next_approve_function_id,process_desc,org_priv_len)
VALUE (3, 2, '000000', '0000', '00', 8, '三级部门负责人审批', 6);
INSERT t_oa_process (process_id, bill_type,current_step,parent_step,next_step,next_approve_function_id,process_desc,org_priv_len)
VALUE (4, 2, '00000000', '000000', '00', 6, '二级部门负责人审批', 5);
INSERT t_oa_process (process_id, bill_type,current_step,parent_step,next_step,process_condition_id,process_desc,org_priv_len)
VALUE (5, 2, '0000000000', '00000000', '00', 2, '单位/部门负责人审批', 3);
INSERT t_oa_process (process_id, bill_type,current_step,parent_step,next_step,process_condition_id,process_desc,org_priv_len)
VALUE (6, 2, '000000000000', '0000000000', '00', 3, '市局分管领导审批', 2);
INSERT t_oa_process (process_id, bill_type,current_step,parent_step,next_step,process_desc,org_priv_len)
VALUE (7, 2, '00000000000000', '000000000000', 'end', '市局负责人审批', 1);

# 年假
# 机关
INSERT t_oa_process (process_id,bill_type,current_step,parent_step,next_step,process_condition_id, process_desc,org_priv_len)
VALUE (8, 1, '00', 'root', '00', 9, '工单申请', 0);
INSERT t_oa_process (process_id, bill_type,current_step,parent_step,next_step,next_approve_function_id,process_desc,org_priv_len)
VALUE (9, 1, '0000', '00', '00', 8, '四级部门负责人审批', 7);
INSERT t_oa_process (process_id, bill_type,current_step,parent_step,next_step,next_approve_function_id,process_desc,org_priv_len)
VALUE (10, 1, '000000', '0000', '00', 8, '三级部门负责人审批', 6);
INSERT t_oa_process (process_id, bill_type,current_step,parent_step,next_step,next_approve_function_id,process_desc,org_priv_len)
VALUE (11, 1, '00000000', '000000', '00', 6, '二级部门负责人审批', 5);
INSERT t_oa_process (process_id, bill_type,current_step,parent_step,next_step,process_condition_id,process_desc,org_priv_len)
VALUE (12, 1, '0000000000', '00000000', '00', 2, '单位/部门负责人审批', 3);
INSERT t_oa_process (process_id, bill_type,current_step,parent_step,next_step,process_condition_id,process_desc,org_priv_len)
VALUE (13, 1, '000000000000', '0000000000', '00', 3, '市局分管领导审批', 2);
INSERT t_oa_process (process_id, bill_type,current_step,parent_step,next_step,process_desc,org_priv_len)
VALUE (14, 1, '00000000000000', '000000000000', 'end', '市局负责人审批', 1);
# 直属单位
INSERT t_oa_process (process_id, bill_type,current_step,parent_step,next_step,next_approve_function_id,process_desc,org_priv_len)
VALUE (15, 1, '0001', '00', '00', 8, '三级部门负责人审批', 7);
INSERT t_oa_process (process_id, bill_type,current_step,parent_step,next_step,next_approve_function_id,process_desc,org_priv_len)
VALUE (16, 1, '000100', '0001', '00', 8, '二级部门负责人审批', 6);
INSERT t_oa_process (process_id, bill_type,current_step,parent_step,next_step,next_approve_function_id,process_desc,org_priv_len)
VALUE (17, 1, '00010000', '000100', '00', 4, '部门负责人审批', 5);
INSERT t_oa_process (process_id, bill_type,current_step,parent_step,next_step,next_approve_function_id,process_desc,org_priv_len)
VALUE (18, 1, '0001000000', '00010000', '00', 6, '单位分管领导审批', 4);
INSERT t_oa_process (process_id, bill_type,current_step,parent_step,next_step,process_condition_id,process_desc,org_priv_len)
VALUE (19, 1, '000100000000', '0001000000', '00', 2, '单位/部门负责人审批', 3);
INSERT t_oa_process (process_id, bill_type,current_step,parent_step,next_step,process_condition_id,process_desc,org_priv_len)
VALUE (20, 1, '00010000000000', '000100000000', '00', 3, '市局分管领导审批', 2);
INSERT t_oa_process (process_id, bill_type,current_step,parent_step,next_step,process_desc,org_priv_len)
VALUE (21, 1, '0001000000000000', '00010000000000', 'end', '市局负责人审批', 1);
