package top.jayu.oa.entity;

import lombok.Data;
import top.jayu.oa.param.BaseParam;

import java.util.Date;

@Data
public class OaBillOpera extends BaseParam {
    private Integer billOperaId;

    private Integer billId;

    private String billCode;

    private Integer billType;

    private String billTypeName;

    private String billStep;

    private Float stepOrgLevel;

    private String billStepName;

    private Integer operaId;

    private String operaName;

    private Integer operaOrgId;

    private String operaOrgName;

    private Byte passFlag;

    private String content;

    private Date createTime;

    private Date beginTime;

    private Date endTime;
}