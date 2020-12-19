package top.jayu.oa.entity;

import lombok.Data;

@Data
public class OaProcessFunction {
    private Integer processFunctionId;

    private String input;

    private String inputValueType;

    private String iocEntityName;

    private String iocEntityMethod;

    private String functionName;

    private String functionDesc;

}