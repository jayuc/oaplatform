package top.jayu.oa.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
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

    private Byte passFlag;  // 是否同意 1表示同意，2表示不同意，0表示创建表单

    private Integer applyId;

    private String applyName;

    private String nextApproveList;

    private String historyApproveList;

    private Integer applyOrgId;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date startTime;

    @JsonFormat(pattern = "yyyy-MM-dd")
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