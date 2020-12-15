package top.jayu.oa.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import top.jayu.oa.param.BaseParam;

import java.util.Date;

@Data
public class OaBill extends BaseParam {
    private Integer id;

    private String employName = "张三";
    private String orgName = "办公室";

    private String code;

    private Byte type;

    private String currentStep;

    private Byte stopFlag;

    private Integer employId;

    private Integer nextEmployId;

    private Integer nextOrgId;

    private Integer orgId;

    private Byte orgOaType;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date startTime;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date endTime;

    private Byte workAge;

    private Short days;

    private Short holidayType;

    private Short peopleNumber;

    private String content;

    private String address;

    private Byte travelTool;

    private Integer amount;

    private String fileUrl1;

    private String fileUrl2;

    private String fileUrl3;

    private String extendContent;

    private Date createTime;

    private Date updateTime;

    private String mark;
}