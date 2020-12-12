package top.jayu.oa.entity;

import lombok.Data;
import lombok.ToString;

@ToString
@Data
public class User {
    private Integer id;

    private String loginName;

    private String password;

    private String name;

    private Integer employId;

    private Integer orgId;

    private Integer roleId;

    private String tel;

    private String mobileTel;

    private Integer age;
}