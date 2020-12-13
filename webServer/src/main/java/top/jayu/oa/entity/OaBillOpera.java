package top.jayu.oa.entity;

import lombok.Data;
import top.jayu.oa.param.BaseParam;

import java.util.Date;

@Data
public class OaBillOpera extends BaseParam {
    private Integer id;

    private Integer billId;

    private String step;

    private Integer approveEmployId;

    private Integer approveOrgId;

    private Byte passFlag;

    private String content;

    private Date createTime;

}