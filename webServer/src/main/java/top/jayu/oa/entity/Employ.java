package top.jayu.oa.entity;

import lombok.Data;

@Data
public class Employ {
    private Integer id;

    private String name;

    private String loginName;

    private String password;

    private String position;

    private Integer orgId;

    private String tel;

    private String mobileTel;

    private Integer age;

    private String address;
}