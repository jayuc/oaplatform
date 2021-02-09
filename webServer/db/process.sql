#
# 2020年12月25日需要执行的数据库脚本
# 余杰 create by 2020/12/25

CREATE database if NOT EXISTS `oa` default character set utf8mb4 collate utf8mb4_unicode_ci;
use `oa`;

SET NAMES utf8mb4;

# 查找上级部门负责人
INSERT t_oa_process_function (process_function_id,input,input_value_type,ioc_entity_name,ioc_entity_method,function_name)
VALUE (8, 'OaBill', 'OaBill', 'orgService', 'findUpOrgLeader', '上级部门负责人');
INSERT t_oa_process_function (process_function_id,input,input_value_type,ioc_entity_name,ioc_entity_method,function_name)
VALUE (9, 'OaBill', 'OaBill', 'orgService', 'findHumanResourceLeader', '人力资源科负责人');
INSERT t_oa_process_function (process_function_id,input,input_value_type,ioc_entity_name,ioc_entity_method,function_name)
VALUE (10, 'OaBill', 'OaBill', 'orgService', 'findOfficeHouseLeader', '市局公司办公室主任');
INSERT t_oa_process_function (process_function_id,input,input_value_type,ioc_entity_name,ioc_entity_method,function_name)
VALUE (11, 'OaBill', 'OaBill', 'orgService', 'findFinanceLeader', '财务科负责人');
INSERT t_oa_process_function (process_function_id,input,input_value_type,ioc_entity_name,ioc_entity_method,function_name)
VALUE (12, 'OaBill', 'OaBill', 'orgService', 'findMaMIngMin', '办公室（马明敏、苗倩）');


INSERT t_oa_process_condition (process_condition_id,input,input_value_type,ioc_entity_name,ioc_entity_method,success_to,fail_to,condition_desc)
VALUE (9, 'applyOrgId', 'Integer', 'orgService', 'orgIfOffice', '00', '01', '是否是机关');

INSERT t_oa_process_condition (process_condition_id,input,input_value_type,ioc_entity_name,ioc_entity_method,success_to,fail_to,condition_desc,fail_approve_function_id)
VALUE (10, 'OaBill', 'OaBill', 'oaBillService', 'yesLess5000', 'end', '00', '5000元（不含）以内', 3);
INSERT t_oa_process_condition (process_condition_id,input,input_value_type,ioc_entity_name,ioc_entity_method,success_to,fail_to,condition_desc,fail_approve_function_id)
VALUE (11, 'OaBill', 'OaBill', 'oaBillService', 'yesLess5000', 'end', '00', '5000元（不含）以内', 2);

# 出差
INSERT t_oa_process (process_id, bill_type,current_step,parent_step,process_desc,org_priv_len)
VALUE (1, 2, '00', 'root', '工单申请', 999);
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
INSERT t_oa_process (process_id,bill_type,current_step,parent_step,process_condition_id, process_desc,org_priv_len)
VALUE (8, 1, '00', 'root', 9, '工单申请', 999);
INSERT t_oa_process (process_id, bill_type,current_step,parent_step,next_step,next_approve_function_id,process_desc,org_priv_len)
VALUE (9, 1, '0000', '00', '00', 8, '四级部门负责人审批', 7);
INSERT t_oa_process (process_id, bill_type,current_step,parent_step,next_step,next_approve_function_id,process_desc,org_priv_len)
VALUE (10, 1, '000000', '0000', '00', 8, '三级部门负责人审批', 6);
INSERT t_oa_process (process_id, bill_type,current_step,parent_step,next_step,next_approve_function_id,process_desc,org_priv_len)
VALUE (11, 1, '00000000', '000000', '00', 6, '二级部门负责人审批', 5);
INSERT t_oa_process (process_id, bill_type,current_step,parent_step,next_step,process_condition_id,process_desc,org_priv_len)
VALUE (12, 1, '0000000000', '00000000', '00', 2, '部门负责人审批', 3);
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
VALUE (19, 1, '000100000000', '0001000000', '00', 2, '单位负责人审批', 3);
INSERT t_oa_process (process_id, bill_type,current_step,parent_step,next_step,process_condition_id,process_desc,org_priv_len)
VALUE (20, 1, '00010000000000', '000100000000', '00', 3, '市局分管领导审批', 2);
INSERT t_oa_process (process_id, bill_type,current_step,parent_step,next_step,process_desc,org_priv_len)
VALUE (21, 1, '0001000000000000', '00010000000000', 'end', '市局负责人审批', 1);

# 经济业务支出前
# 机关
INSERT t_oa_process (process_id,bill_type,current_step,parent_step,process_condition_id, process_desc,org_priv_len)
VALUE (22, 3, '00', 'root', 9, '工单申请', 999);
INSERT t_oa_process (process_id, bill_type,current_step,parent_step,next_step,next_approve_function_id,process_desc,org_priv_len)
VALUE (23, 3, '0000', '00', '00', 8, '四级部门负责人审批', 7);
INSERT t_oa_process (process_id, bill_type,current_step,parent_step,next_step,next_approve_function_id,process_desc,org_priv_len)
VALUE (24, 3, '000000', '0000', '00', 8, '三级部门负责人审批', 6);
INSERT t_oa_process (process_id, bill_type,current_step,parent_step,next_step,next_approve_function_id,process_desc,org_priv_len)
VALUE (25, 3, '00000000', '000000', '00', 6, '二级部门负责人审批', 5);
INSERT t_oa_process (process_id, bill_type,current_step,parent_step,next_step,next_approve_function_id,process_desc,org_priv_len)
VALUE (26, 3, '0000000000', '00000000', '00', 2, '部门负责人审批', 3);
INSERT t_oa_process (process_id, bill_type,current_step,parent_step,next_step,process_condition_id,process_desc,org_priv_len)
VALUE (27, 3, '000000000000', '0000000000', '00', 10, '市局分管领导审批', 2);
INSERT t_oa_process (process_id, bill_type,current_step,parent_step,next_step,process_desc,org_priv_len)
VALUE (28, 3, '00000000000000', '000000000000', 'end', '市局负责人审批', 1);
# 直属单位
INSERT t_oa_process (process_id, bill_type,current_step,parent_step,next_step,next_approve_function_id,process_desc,org_priv_len)
VALUE (29, 3, '0001', '00', '00', 8, '三级部门负责人审批', 7);
INSERT t_oa_process (process_id, bill_type,current_step,parent_step,next_step,next_approve_function_id,process_desc,org_priv_len)
VALUE (30, 3, '000100', '0001', '00', 8, '二级部门负责人审批', 6);
INSERT t_oa_process (process_id, bill_type,current_step,parent_step,next_step,next_approve_function_id,process_desc,org_priv_len)
VALUE (31, 3, '00010000', '000100', '00', 4, '部门负责人审批', 5);
INSERT t_oa_process (process_id, bill_type,current_step,parent_step,next_step,next_approve_function_id,process_desc,org_priv_len)
VALUE (32, 3, '0001000000', '00010000', '00', 6, '单位分管领导审批', 4);
INSERT t_oa_process (process_id, bill_type,current_step,parent_step,next_step,process_condition_id,process_desc,org_priv_len)
VALUE (33, 3, '000100000000', '0001000000', '00', 11, '单位负责人审批', 3);
INSERT t_oa_process (process_id, bill_type,current_step,parent_step,next_step,next_approve_function_id,process_desc,org_priv_len)
VALUE (34, 3, '00010000000000', '000100000000', '00', 3, '市局分管领导审批', 2);
INSERT t_oa_process (process_id, bill_type,current_step,parent_step,next_step,process_desc,org_priv_len)
VALUE (35, 3, '0001000000000000', '00010000000000', 'end', '市局负责人审批', 1);

# 培训申请
INSERT t_oa_process (process_id, bill_type,current_step,parent_step,process_desc,org_priv_len)
VALUE (36, 4, '00', 'root', '工单申请', 999);
INSERT t_oa_process (process_id, bill_type,current_step,parent_step,next_step,next_approve_function_id,process_desc,org_priv_len)
VALUE (37, 4, '0000', '00', '00', 8, '四级部门负责人审批', 7);
INSERT t_oa_process (process_id, bill_type,current_step,parent_step,next_step,next_approve_function_id,process_desc,org_priv_len)
VALUE (38, 4, '000000', '0000', '00', 8, '三级部门负责人审批', 6);
INSERT t_oa_process (process_id, bill_type,current_step,parent_step,next_step,next_approve_function_id,process_desc,org_priv_len)
VALUE (39, 4, '00000000', '000000', '00', 6, '二级部门负责人审批', 5);
INSERT t_oa_process (process_id, bill_type,current_step,parent_step,next_step,next_approve_function_id,process_desc,org_priv_len)
VALUE (40, 4, '0000000000', '00000000', '00', 9, '单位/部门负责人审批', 3);
INSERT t_oa_process (process_id, bill_type,current_step,parent_step,next_step,next_approve_function_id,process_desc,org_priv_len)
VALUE (41, 4, '000000000000', '0000000000', '00', 2, '人力资源科负责人审批', 3.5);
INSERT t_oa_process (process_id, bill_type,current_step,parent_step,next_step,process_condition_id,process_desc,org_priv_len)
VALUE (42, 4, '00000000000000', '000000000000', '00', 10, '市局分管领导审批', 2);
INSERT t_oa_process (process_id, bill_type,current_step,parent_step,next_step,process_desc,org_priv_len)
VALUE (43, 4, '0000000000000000', '00000000000000', 'end', '市局负责人审批', 1);

# 市局介绍信开具
INSERT t_oa_process (process_id, bill_type,current_step,parent_step,process_desc,org_priv_len)
VALUE (44, 10, '00', 'root', '工单申请', 999);
INSERT t_oa_process (process_id, bill_type,current_step,parent_step,next_step,next_approve_function_id,process_desc,org_priv_len)
VALUE (45, 10, '0000', '00', '00', 8, '四级部门负责人审批', 7);
INSERT t_oa_process (process_id, bill_type,current_step,parent_step,next_step,next_approve_function_id,process_desc,org_priv_len)
VALUE (46, 10, '000000', '0000', '00', 8, '三级部门负责人审批', 6);
INSERT t_oa_process (process_id, bill_type,current_step,parent_step,next_step,next_approve_function_id,process_desc,org_priv_len)
VALUE (47, 10, '00000000', '000000', '00', 6, '二级部门负责人审批', 5);
INSERT t_oa_process (process_id, bill_type,current_step,parent_step,next_step,next_approve_function_id,process_desc,org_priv_len)
VALUE (48, 10, '0000000000', '00000000', '00', 10, '单位/部门负责人审批', 3);
INSERT t_oa_process (process_id, bill_type,current_step,parent_step,next_step,process_desc,org_priv_len)
VALUE (49, 10, '000000000000', '0000000000', 'end', '市局办公室主任审批', 3.5);

# 市局证件
INSERT t_oa_process (process_id, bill_type,current_step,parent_step,process_desc,org_priv_len)
VALUE (50, 11, '00', 'root', '工单申请', 999);
INSERT t_oa_process (process_id, bill_type,current_step,parent_step,next_step,next_approve_function_id,process_desc,org_priv_len)
VALUE (51, 11, '0000', '00', '00', 8, '四级部门负责人审批', 7);
INSERT t_oa_process (process_id, bill_type,current_step,parent_step,next_step,next_approve_function_id,process_desc,org_priv_len)
VALUE (52, 11, '000000', '0000', '00', 8, '三级部门负责人审批', 6);
INSERT t_oa_process (process_id, bill_type,current_step,parent_step,next_step,next_approve_function_id,process_desc,org_priv_len)
VALUE (53, 11, '00000000', '000000', '00', 6, '二级部门负责人审批', 5);
INSERT t_oa_process (process_id, bill_type,current_step,parent_step,next_step,next_approve_function_id,process_desc,org_priv_len)
VALUE (54, 11, '0000000000', '00000000', '00', 10, '单位/部门负责人审批', 3);
INSERT t_oa_process (process_id, bill_type,current_step,parent_step,next_step,process_desc,org_priv_len)
VALUE (55, 11, '000000000000', '0000000000', 'end', '市局办公室主任审批', 3.5);

# 交通工具调整
INSERT t_oa_process (process_id, bill_type,current_step,parent_step,process_desc,org_priv_len)
VALUE (56, 6, '00', 'root', '工单申请', 999);
INSERT t_oa_process (process_id, bill_type,current_step,parent_step,next_step,next_approve_function_id,process_desc,org_priv_len)
VALUE (57, 6, '0000', '00', '00', 8, '四级部门负责人审批', 7);
INSERT t_oa_process (process_id, bill_type,current_step,parent_step,next_step,next_approve_function_id,process_desc,org_priv_len)
VALUE (58, 6, '000000', '0000', '00', 8, '三级部门负责人审批', 6);
INSERT t_oa_process (process_id, bill_type,current_step,parent_step,next_step,next_approve_function_id,process_desc,org_priv_len)
VALUE (59, 6, '00000000', '000000', '00', 6, '二级部门负责人审批', 5);
INSERT t_oa_process (process_id, bill_type,current_step,parent_step,next_step,next_approve_function_id,process_desc,org_priv_len)
VALUE (60, 6, '0000000000', '00000000', '00', 3, '单位/部门负责人审批', 3);
INSERT t_oa_process (process_id, bill_type,current_step,parent_step,next_step,process_desc,org_priv_len)
VALUE (61, 6, '000000000000', '0000000000', 'end', '市局负责人审批', 3.5);

# 调阅会计档案
INSERT t_oa_process (process_id, bill_type,current_step,parent_step,process_desc,org_priv_len)
VALUE (62, 8, '00', 'root', '工单申请', 999);
INSERT t_oa_process (process_id, bill_type,current_step,parent_step,next_step,next_approve_function_id,process_desc,org_priv_len)
VALUE (63, 8, '0000', '00', '00', 8, '四级部门负责人审批', 7);
INSERT t_oa_process (process_id, bill_type,current_step,parent_step,next_step,next_approve_function_id,process_desc,org_priv_len)
VALUE (64, 8, '000000', '0000', '00', 8, '三级部门负责人审批', 6);
INSERT t_oa_process (process_id, bill_type,current_step,parent_step,next_step,next_approve_function_id,process_desc,org_priv_len)
VALUE (65, 8, '00000000', '000000', '00', 6, '二级部门负责人审批', 5);
INSERT t_oa_process (process_id, bill_type,current_step,parent_step,next_step,next_approve_function_id,process_desc,org_priv_len)
VALUE (66, 8, '0000000000', '00000000', '00', 11, '单位/部门负责人审批', 3);
INSERT t_oa_process (process_id, bill_type,current_step,parent_step,next_step,process_desc,org_priv_len)
VALUE (67, 8, '000000000000', '0000000000', 'end', '财务科负责人审批', 3.5);

# 会议室
INSERT t_oa_process (process_id,bill_type,current_step,parent_step,next_step,next_approve_function_id,process_desc,org_priv_len)
VALUE (68, 5, '00', 'root', '00', 12, '工单申请', 999);
INSERT t_oa_process (process_id,bill_type,current_step,parent_step,next_step,process_desc,org_priv_len)
VALUE (69, 5, '0000', '00', 'end', '办公室（马明敏、苗倩）审批', 0);


# 流程步骤
INSERT INTO t_oa_process_step (step_id,step_name,step_type,success_to,fail_to,create_time)
VALUE (1,'单位/部门负责人审批','1','00','',now());
INSERT INTO t_oa_process_step (step_id,step_name,step_type,success_to,fail_to,create_time)
VALUE (2,'科级干部','2','00','end',now());
INSERT INTO t_oa_process_step (step_id,step_name,step_type,success_to,fail_to,create_time)
VALUE (3,'市局（公司）分管领导审批','1','00','',now());
INSERT INTO t_oa_process_step (step_id,step_name,step_type,success_to,fail_to,create_time)
VALUE (4,'单位/部门负责人','2','00','end',now());
INSERT INTO t_oa_process_step (step_id,step_name,step_type,success_to,fail_to,create_time)
VALUE (5,'市局（公司）主要负责人审批','1','00','',now());
INSERT INTO t_oa_process_step (step_id,step_name,step_type,success_to,fail_to,create_time)
VALUE (6,'结束','3','end','',now());
INSERT INTO t_oa_process_step (step_id,step_name,step_type,success_to,fail_to,create_time)
VALUE (7,'类别','2','00','01',now());
INSERT INTO t_oa_process_step (step_id,step_name,step_type,success_to,fail_to,create_time)
VALUE (8,'部门负责人（直属）审批','1','00','',now());
INSERT INTO t_oa_process_step (step_id,step_name,step_type,success_to,fail_to,create_time)
VALUE (9,'单位分管领导审批','1','00','',now());
INSERT INTO t_oa_process_step (step_id,step_name,step_type,success_to,fail_to,create_time)
VALUE (10,'单位负责人审批','1','00','',now());
INSERT INTO t_oa_process_step (step_id,step_name,step_type,success_to,fail_to,create_time)
VALUE (11,'部门负责人（机关）审批','1','00','',now());

