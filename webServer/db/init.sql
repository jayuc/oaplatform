#
# 余杰 create by 2020/12/11

CREATE database if NOT EXISTS `oa` default character set utf8mb4 collate utf8mb4_unicode_ci;
use `oa`;

SET NAMES utf8mb4;

# 职员表
CREATE TABLE `t_sys_employ` (
  `employ_id` int(11) NOT NULL AUTO_INCREMENT,
  `employ_name` varchar(16) DEFAULT NULL COMMENT '姓名',
  `employ_code` varchar(32) UNIQUE DEFAULT NULL COMMENT '职员编号',
  `position` varchar(32) DEFAULT NULL COMMENT '岗位',
  `work_age` tinyint DEFAULT NULL COMMENT '工龄',
  `yes_chief` tinyint DEFAULT NULL COMMENT '是否是科级干部',
  `org_id` int(11) DEFAULT NULL COMMENT '机构id',
  `org_code` varchar(32) DEFAULT NULL COMMENT '机构编号',
  `tel` varchar(16) DEFAULT NULL COMMENT '联系方式',
  `mobile_tel` varchar(16) DEFAULT NULL COMMENT '手机号',
  `age` int(11) DEFAULT NULL COMMENT '年龄',
  `sex` tinyint DEFAULT NULL COMMENT '性别',
  `address` varchar(255) DEFAULT NULL COMMENT '家庭地址',
  PRIMARY KEY (`employ_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT = '职员表';

# 流程单表
CREATE TABLE `t_oa_bill` (
  `bill_id` int(11) NOT NULL AUTO_INCREMENT,
  `bill_code` varchar(32) UNIQUE DEFAULT NULL COMMENT '编号',
  `bill_type` int NOT NULL COMMENT '表单类别,数据字典：1',
  `current_step` varchar(32) NOT NULL COMMENT '表单当前步骤，可以动态调整',
  `stop_flag` tinyint DEFAULT NULL COMMENT '终止标记，1表示终止 2表示未终止',
  `apply_id` int(11) DEFAULT NULL COMMENT '申请人id',
  `next_approve_list` varchar(128) DEFAULT NULL COMMENT '下一步审批人id列表，中间用英文逗号隔开，例如,2,34,',
  `history_approve_list` varchar(256) DEFAULT NULL COMMENT '历史审批人id列表，中间用英文逗号隔开，例如,2,34,',
  `apply_org_id` int(11) DEFAULT NULL COMMENT '申请人机构id',
  `apply_org_yes_office` tinyint DEFAULT NULL COMMENT '申请人机构是否是机关,1表示是',
  `apply_org_code_priv` varchar(32) DEFAULT NULL COMMENT '申请人机构权限编码,用来判断机构层级',
  `approve_org_code_priv` varchar(32) DEFAULT NULL COMMENT '审批人机构权限编码,用来判断机构层级',
  `pass_flag` tinyint DEFAULT NULL COMMENT '是否同意 1同意，2不同意，0创建申请单',
  `firm_type` tinyint NOT NULL COMMENT '业务域,数据字典：5',
  `approve_id` int(11) DEFAULT NULL COMMENT '最近一次的审批人id',
  `approve_name` varchar(16) DEFAULT NULL COMMENT '最近一次的审批人姓名',
  `approve_content` varchar(256) DEFAULT NULL COMMENT '最近一次审批意见',
  `start_time` datetime DEFAULT NULL COMMENT '开始日期',
  `end_time` datetime DEFAULT NULL COMMENT '结束日期',
  `work_age` tinyint DEFAULT NULL COMMENT '工龄',
  `holiday_type` tinyint DEFAULT NULL COMMENT '休假标准',
  `days` smallint DEFAULT NULL COMMENT '天数',
  `people_list` varchar(256) DEFAULT NULL COMMENT '出差人员',
  `people_number` smallint DEFAULT NULL COMMENT '人数',
  `content` varchar(1024) DEFAULT NULL COMMENT '内容（比如出差内容）',
  `address` varchar(128) DEFAULT NULL COMMENT '地点',
  `travel_tool` tinyint DEFAULT NULL COMMENT '出行工具',
  `amount` int(11) DEFAULT NULL COMMENT '金额',
  `file_url1` varchar(256) DEFAULT NULL COMMENT '附件1地址',
  `file_url2` varchar(256) DEFAULT NULL COMMENT '附件2地址',
  `file_url3` varchar(256) DEFAULT NULL COMMENT '附件3地址',
  `history_process_list` varchar(256) DEFAULT NULL COMMENT '历史流程节点记录',
  `extend_content` varchar(2048) DEFAULT NULL COMMENT '扩展内容（存放json字符串）',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `mark` varchar(256) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`bill_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT = '流程单表';

# 流程表
CREATE TABLE `t_oa_all_process` (
  `one_process_id` int(11) NOT NULL AUTO_INCREMENT,
  `bill_type` int NOT NULL COMMENT '动态表单类别，可与表单类别进行动态匹配',
  `process_name` varchar(32) DEFAULT NULL COMMENT '流程名称',
  `process_desc` varchar(256) DEFAULT NULL COMMENT '流程描述',
  `create_person_id` int(11) DEFAULT NULL COMMENT '创建人id，对应的用户id',
  `create_person` varchar(16) DEFAULT NULL COMMENT '创建人',
  `update_person_id` int(11) DEFAULT NULL COMMENT '更新人id，对应的用户id',
  `update_person` varchar(16) DEFAULT NULL COMMENT '更新人',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `mark` varchar(128) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`one_process_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT = '流程表';

# 流程步骤表
CREATE TABLE `t_oa_process` (
  `process_id` int(11) NOT NULL AUTO_INCREMENT,
  `bill_type` int NOT NULL COMMENT '动态表单类别，可与表单类别进行动态匹配',
  `current_step` varchar(32) DEFAULT NULL COMMENT '当前步骤',
  `org_priv_len` FLOAT NOT NULL COMMENT '机构权限长度，通过机构权限长度判断机构所处的层级',
  `next_step` varchar(32) DEFAULT NULL COMMENT '下一步骤名称',
  `next_approve_function_id` int(11) DEFAULT NULL COMMENT '下一步审批人查询方法id',
  `parent_step` varchar(32) DEFAULT NULL COMMENT '父步骤',
  `process_condition_id` int(11) DEFAULT NULL COMMENT '流程条件id',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `process_desc` varchar(32) DEFAULT NULL COMMENT '流程描述',
  `mark` varchar(128) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`process_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT = '流程步骤表';

# 流程条件
CREATE TABLE `t_oa_process_condition` (
  `process_condition_id` int(11) NOT NULL AUTO_INCREMENT,
  `input` varchar(32) NOT NULL COMMENT '条件输入,例如：userId',
  `input_value_type` varchar(32) NOT NULL COMMENT '条件输入值类型,例如：String,Integer',
  `ioc_entity_name` varchar(32) NOT NULL COMMENT 'spring容器中实体名字',
  `ioc_entity_method` varchar(32) NOT NULL COMMENT '方法',
  `success_to` varchar(32) NOT NULL COMMENT '结果为true流向步骤名',
  `success_condition_id` int(11) DEFAULT NULL COMMENT '流程条件id,结果为true后,如果不为空，则继续进入此条件',
  `success_approve_function_id` int(11) DEFAULT NULL COMMENT '查询审批人方法id,结果为true时通过此方法查询审批人',
  `fail_to` varchar(32) NOT NULL COMMENT '结果为false的流向的步骤名',
  `fail_condition_id` int(11) DEFAULT NULL COMMENT '流程条件id,结果为false后,如果不为空，则继续进入此条件',
  `fail_approve_function_id` int(11) DEFAULT NULL COMMENT '查询审批人方法id,结果为false时通过此方法查询审批人',
  `condition_desc` varchar(1024) DEFAULT NULL COMMENT '条件描述',
  PRIMARY KEY (`process_condition_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT = '流程条件表';

# 流程方法
CREATE TABLE `t_oa_process_function` (
  `process_function_id` int(11) NOT NULL AUTO_INCREMENT,
  `input` varchar(32) NOT NULL COMMENT '条件输入,例如：userId',
  `input_value_type` varchar(32) NOT NULL COMMENT '条件输入值类型,例如：String,Integer',
  `ioc_entity_name` varchar(32) NOT NULL COMMENT 'spring容器中实体名字',
  `ioc_entity_method` varchar(32) NOT NULL COMMENT '方法',
  `function_name` varchar(32) DEFAULT NULL COMMENT '方法名，例如部门负责人',
  `function_desc` varchar(1024) DEFAULT NULL COMMENT '条件描述',
  PRIMARY KEY (`process_function_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT = '流程方法表';

# 流程步骤
CREATE TABLE `t_oa_process_step` (
  `step_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '步骤id（主键）',
  `step_name` varchar(32) NOT NULL COMMENT '步骤名称',
  `step_type` tinyint NOT NULL COMMENT '步骤类型，1：审批 2：条件 3：结束 4：备案',
  `success_to` varchar(32) NOT NULL COMMENT '条件成功去向',
  `fail_to` varchar(32) NOT NULL COMMENT '条件失败去向',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`step_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT = '流程步骤表';

# 流程单操作记录
CREATE TABLE `t_oa_bill_opera` (
  `bill_opera_id` int(11) NOT NULL AUTO_INCREMENT,
  `bill_id` int(11) DEFAULT NULL COMMENT '工单id',
  `bill_code` varchar(32) DEFAULT NULL COMMENT '编号',
  `bill_type` int NOT NULL COMMENT '动态表单类别，可与表单类别进行动态匹配',
  `bill_type_name` varchar(64) DEFAULT NULL COMMENT '工单类型名称',
  `bill_step` varchar(32) NOT NULL COMMENT '流程的步骤',
  `step_org_level` FLOAT DEFAULT NULL COMMENT '此次流程在机构中的层级',
  `bill_step_name` varchar(32) DEFAULT NULL COMMENT '流程步骤名称',
  `opera_id` int(11) DEFAULT NULL COMMENT '操作人id',
  `opera_name` varchar(64) DEFAULT NULL COMMENT '操作人名称',
  `opera_org_id` int(11) DEFAULT NULL COMMENT '操作人机构id',
  `opera_org_name` varchar(64) DEFAULT NULL COMMENT '操作人机构名称',
  `pass_flag` tinyint NOT NULL COMMENT '是否同意 1同意，2不同意，0创建申请单',
  `content` varchar(1024) DEFAULT NULL COMMENT '审批意见',
  `create_time` datetime DEFAULT NULL COMMENT '审批时间',
  PRIMARY KEY (`bill_opera_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT = '流程操作记录表';


# 模拟数据
INSERT t_oa_process_function (input,input_value_type,ioc_entity_name,ioc_entity_method,function_name)
VALUE ('OaBill', 'OaBill', 'orgService', 'findOrgLeader', '部门负责人');
INSERT t_oa_process_function (input,input_value_type,ioc_entity_name,ioc_entity_method,function_name)
VALUE ('OaBill', 'OaBill', 'orgService', 'findCompanyDeputy', '市局分管领导');
INSERT t_oa_process_function (input,input_value_type,ioc_entity_name,ioc_entity_method,function_name)
VALUE ('OaBill', 'OaBill', 'orgService', 'findCompanyLeader', '市局负责人');
INSERT t_oa_process_function (input,input_value_type,ioc_entity_name,ioc_entity_method,function_name)
VALUE ('OaBill', 'OaBill', 'orgService', 'findUnitDeputy', '单位分管领导');
INSERT t_oa_process_function (input,input_value_type,ioc_entity_name,ioc_entity_method,function_name)
VALUE ('OaBill', 'OaBill', 'orgService', 'findUnitLeader', '单位负责人');

# 出差
INSERT t_oa_process_function (process_function_id,input,input_value_type,ioc_entity_name,ioc_entity_method,function_name)
VALUE (6, 'OaBill', 'OaBill', 'orgService', 'findUnitLeader', '单位/部门负责人');
INSERT t_oa_process_function (process_function_id,input,input_value_type,ioc_entity_name,ioc_entity_method,function_name)
VALUE (7, 'applyOrgCodePriv', 'String', 'orgService', 'findCompanyDeputy', '市局分管领导');

INSERT t_oa_process_condition (input,input_value_type,ioc_entity_name,ioc_entity_method,success_to,fail_to,condition_desc,
success_approve_function_id,fail_approve_function_id, success_condition_id, fail_condition_id)
VALUE ('applyOrgId', 'Integer', 'orgService', 'orgIfOffice', '00', '01', '是否是机关', 1, 1, 4, 4);
INSERT t_oa_process_condition (input,input_value_type,ioc_entity_name,ioc_entity_method,success_to,fail_to,condition_desc,success_approve_function_id)
VALUE ('applyId', 'Integer', 'userService', 'userIfChief', '00', 'end', '是否是科级干部',2);
INSERT t_oa_process_condition (input,input_value_type,ioc_entity_name,ioc_entity_method,success_to,fail_to,condition_desc,success_approve_function_id)
VALUE ('applyId', 'Integer', 'userService', 'userIfLeader', '00', 'end', '是否是部门/单位负责人',3);
INSERT t_oa_process_condition (input,input_value_type,ioc_entity_name,ioc_entity_method,success_to,fail_to,condition_desc,
success_approve_function_id, fail_approve_function_id, success_condition_id)
VALUE ('applyId', 'Integer', 'userService', 'userIfLeader', '0000', '00', '是否是部门负责人',2, 1, 6);
INSERT t_oa_process_condition (input,input_value_type,ioc_entity_name,ioc_entity_method,success_to,fail_to,condition_desc)
VALUE ('applyId', 'Integer', 'userService', 'userIfCompanyLeader', 'end', 'end', '是否是市局负责人');
INSERT t_oa_process_condition (input,input_value_type,ioc_entity_name,ioc_entity_method,success_to,fail_to,condition_desc,
fail_approve_function_id)
VALUE ('applyId', 'Integer', 'userService', 'userIfCompanyLeader', 'end', '0000', '是否是市局负责人', 2);

# 出差
INSERT t_oa_process_condition (process_condition_id, input,input_value_type,ioc_entity_name,ioc_entity_method,
success_to,fail_to,condition_desc,success_approve_function_id)
VALUE (7, 'applyId', 'Integer', 'userService', 'userIfChief', '00', 'end', '是否是科级干部', 7);



INSERT t_sys_user (user_id,user_name,login_name,password,org_id,role_id)
values (9999,'超级管理员','admin','e10adc3949ba59abbe56e057f20f883e',-1,-1);