package top.jayu.oa.entity;

import lombok.Data;

import java.util.Date;

@Data
public class OaBillPending {
    private String title;
    private int type;
    private String typeName;
    private int billId;
    private String billType;
    private Date applyTime;
    private String applyName;
    private String billTypeName;
}
