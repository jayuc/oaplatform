package top.jayu.oa.entity;

import lombok.Data;
import top.jayu.oa.param.BaseParam;

@Data
public class Function extends BaseParam {

    private Integer functionId;

    private String functionCode;

    private String menuCode;

    private String functionName;

    private Byte enableFlag;

    private String mark;

}