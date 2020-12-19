package top.jayu.oa.entity;

import lombok.Data;

import java.util.Date;

@Data
public class OaProcess {
    private Integer processId;

    private Byte billType;

    private String currentStep;

    private String nextStep;

    private Integer nextApproveFunctionId;

    private String parentStep;

    private Integer processConditionId;

    private Date createTime;

    private Date updateTime;

    private String mark;
}