package top.jayu.oa.entity;

import lombok.Data;
import top.jayu.oa.param.BaseParam;

@Data
public class Org extends BaseParam {
    private Integer orgId;

    private Integer parentId;

    private String parentCode;

    private String orgName;

    private String shortOrgName;

    private String orgCode;

    private String orgCodePriv;

    private Byte yesOffice;

    private Byte yesLeader;

    private Integer leaderId;

    private Integer deputyId;

    private String leaderName;

    private String deputyName;

    private String address;

    private String tel;

    private Byte sort;
}