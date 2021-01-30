package top.jayu.oa.entity;

import lombok.Data;

@Data
public class RolePermission {

    private Integer id;

    private Integer roleId;

    private Integer functionId;

    private String functionCode;

    private String functionName;

    private Byte type;

}