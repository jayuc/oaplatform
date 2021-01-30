package top.jayu.oa.entity;

import lombok.Data;

@Data
public class UserRole {

    private Integer id;

    private Integer userId;

    private Integer roleId;

    private String roleName;

}