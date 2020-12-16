package top.jayu.oa.entity;

import lombok.Data;
import top.jayu.oa.param.BaseParam;

@Data
public class User extends BaseParam {
    private Integer userId;

    private String loginName;

    private String userName;

    private String userCode;

    private Integer employId;

    private Byte yesChief;

    private Integer orgId;

    private String position;

    private Integer roleId;

    private String tel;

    private String mobileTel;

    private Integer age;

    private Byte sex;
}