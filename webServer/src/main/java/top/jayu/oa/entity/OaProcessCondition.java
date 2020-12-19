package top.jayu.oa.entity;

import lombok.Data;

@Data
public class OaProcessCondition {
    private Integer processConditionId;

    private String input;

    private String inputValueType;

    private String iocEntityName;

    private String iocEntityMethod;

    private String successTo;

    private Integer successConditionId;

    private Integer successApproveFunctionId;

    private String failTo;

    private Integer failConditionId;

    private Integer failApproveFunctionId;

    private String conditionDesc;

}