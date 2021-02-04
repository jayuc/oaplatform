package top.jayu.oa.entity;

import lombok.Data;
import top.jayu.oa.param.BaseParam;

import java.util.Date;

@Data
public class OaAllProcess extends BaseParam {

    private Integer oneProcessId;

    private Integer billType;

    private String processName;

    private String processDesc;

    private Integer createPersonId;

    private String createPerson;

    private Integer updatePersonId;

    private String updatePerson;

    private Date createTime;

    private Date updateTime;

    private String mark;

}