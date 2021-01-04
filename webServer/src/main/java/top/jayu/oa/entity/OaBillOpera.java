package top.jayu.oa.entity;

import lombok.Data;
import top.jayu.oa.param.BaseParam;

import java.util.Date;

@Data
public class OaBillOpera extends BaseParam {
    private Integer billOperaId;

    private String billCode;

    private Byte billType;

    private String billStep;

    private Integer operaId;

    private Byte passFlag;

    private String content;

    private Date createTime;

    private Date beginTime;

    private Date endTime;
}