package top.jayu.oa.entity;

import lombok.Data;
import top.jayu.oa.param.BaseParam;

@Data
public class User extends BaseParam {
    private Integer userId;

    private String loginName;

    private String password;

    private String userName;

    private Integer employId;

    private Byte yesChief;

    private Integer orgId;

    private Integer roleId;

    private String tel;

    private String mobileTel;

    private Integer age;
}