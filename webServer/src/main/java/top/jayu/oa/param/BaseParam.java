package top.jayu.oa.param;

import lombok.Data;

@Data
public class BaseParam {
    private Integer pageNumber;
    private Integer pageSize;
    private Integer userId;
    private Integer orgId;
    private String orgCode;
    private String orgCodePriv;
}
