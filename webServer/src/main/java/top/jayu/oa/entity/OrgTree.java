package top.jayu.oa.entity;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 余杰 on 2020/12/16 11:03
 */

@Data
public class OrgTree {
    private int orgId;
    private String orgName;
    private String orgCode;
    private List<OrgTree> children;
    private Org attribute;

    public OrgTree(int orgId, String orgCode, String orgName, Org attribute) {
        this.orgId = orgId;
        this.orgCode = orgCode;
        this.orgName = orgName;
        this.attribute = attribute;
        this.children = new ArrayList<>();
    }
}
