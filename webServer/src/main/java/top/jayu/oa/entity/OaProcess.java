package top.jayu.oa.entity;

import lombok.Data;

import java.util.Date;

@Data
public class OaProcess {
    private Integer processId;

    private Short processType;

    private String prevStep;

    private String nextStep;

    private Integer approveOrgId;

    private Date createTime;

    private Date updateTime;

    private String mark;
}