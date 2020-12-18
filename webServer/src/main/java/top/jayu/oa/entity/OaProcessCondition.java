package top.jayu.oa.entity;

import lombok.Data;

@Data
public class OaProcessCondition {
    private Integer processConditionId;

    private String input;

    private String inputValueType;

    private String iocEntityName;

    private String iocEntityMethod;

    private String seccessTo;

    private Integer seccessConditionId;

    private String failTo;

    private Integer failConditionId;

    private String conditionDesc;
}