package top.jayu.oa.entity;

import lombok.Data;
import top.jayu.oa.param.BaseParam;

@Data
public class Employ extends BaseParam {
    private Integer employId;

    private String employName;

    private String employCode;

    private String position;

    private Byte workAge;

    private Byte yesChief;

    private Integer orgId;

    private String tel;

    private String mobileTel;

    private Integer age;

    private String address;

    private Byte sex;
}