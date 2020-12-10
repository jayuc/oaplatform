package top.jayu.oa.entity;

import lombok.Data;

import java.io.Serializable;

@Data
public class User implements Serializable {
    private static final long serialVersionUID = 1L;
    private int id;
    private String name;
    private int orgId;
    private int roleId;
    private String tel;
    private int age;
}
