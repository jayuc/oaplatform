package top.jayu.oa.entity;

import lombok.Data;

@Data
public class Org {
    private Integer id;

    private Integer parentId;

    private String name;

    private String code;

    private String codePriv;

    private String address;

    private String tel;
}