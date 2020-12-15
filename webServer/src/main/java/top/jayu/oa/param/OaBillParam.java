package top.jayu.oa.param;

import lombok.Data;

@Data
public class OaBillParam {

    private Integer id;

    private String currentStep;

    private Byte stopFlag;

    private Integer nextEmployId;

    private Integer nextOrgId;

    private String content;

    private String extendContent;

    private String mark;

}
