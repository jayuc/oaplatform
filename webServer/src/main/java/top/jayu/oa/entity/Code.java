package top.jayu.oa.entity;

import lombok.Data;
import top.jayu.oa.param.BaseParam;

@Data
public class Code extends BaseParam {
    private Integer id;

    private Short code;

    private String codeNo;

    private String name;

    private Byte enableFlag;

    private String mark;

}