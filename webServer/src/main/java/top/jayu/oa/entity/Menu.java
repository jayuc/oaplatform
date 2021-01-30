package top.jayu.oa.entity;

import lombok.Data;
import top.jayu.oa.param.BaseParam;

@Data
public class Menu extends BaseParam {

    private Integer menuId;

    private String menuCode;

    private String parentCode;

    private String menuName;

    private String menuPath;

    private String menuImage;

    private Byte level;

    private Byte sort;

    private Byte enableFlag;

    private String mark;

}