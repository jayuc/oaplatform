package top.jayu.oa.entity;

import lombok.Data;

@Data
public class ProcessStep extends OaProcess {

    private String successTo;

    private Integer successApproveFunctionId;

    private String failTo;

    private Integer failApproveFunctionId;

}
