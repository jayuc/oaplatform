package top.jayu.oa.entity;

import lombok.Data;

@Data
public class Code {
    private Integer id;

    private Short code;

    private Byte codeNo;

    private String name;

    private Byte enableFlag;

    private String mark;

}