package top.jayu.oa.param;

import lombok.Data;

@Data
public class BaseParam {
    private Integer pageNumber;
    private Integer pageSize;
    private Integer currentUserId;
    private Integer currentOrgId;
    private Integer currentRoleId;
    private Integer currentEmployId;
    private String currentOrgCode;
    private String currentOrgCodePriv;
}
