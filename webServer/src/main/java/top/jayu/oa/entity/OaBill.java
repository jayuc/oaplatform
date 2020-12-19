package top.jayu.oa.entity;

import lombok.Data;
import top.jayu.oa.param.BaseParam;

import java.util.Date;

@Data
public class OaBill extends BaseParam {
    private Integer billId;

    private String billCode;

    private Byte billType;

    private String currentStep;

    private Byte stopFlag;

    private Integer applyId;

    private String nextApproveList;

    private String historyApproveList;

    private Integer orgId;

    private Byte orgOaType;

    private Date startTime;

    private Date endTime;

    private Byte workAge;

    private Byte holidayType;

    private Short days;

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