package top.jayu.oa.entity;

import lombok.Data;

import java.util.Date;

@Data
public class OaBillOpera {
    private Integer billOperaId;

    private Integer billId;

    private Byte billType;

    private String billStep;

    private Integer approveEmployId;

    private Integer approveOrgId;

    private Byte passFlag;

    private String content;

    private Date createTime;
}