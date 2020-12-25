package top.jayu.oa.entity;

import lombok.Data;
import lombok.ToString;

import java.util.Date;

@Data
@ToString
public class OaProcess {
    private Integer processId;

    private Byte billType;

    private Byte orgPrivLen;

    private String currentStep;

    private String nextStep;

    private Integer nextApproveFunctionId;

    private String parentStep;

    private Integer processConditionId;

    private Date createTime;

    private Date updateTime;

    private String processDesc;

    private String mark;
}