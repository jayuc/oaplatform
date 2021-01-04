package top.jayu.oa.entity;

import lombok.Data;
import top.jayu.oa.param.BaseParam;

@Data
public class Role extends BaseParam {
    private Integer roleId;

    private String roleName;

    private Byte enableFlag;

    private String mark;
}