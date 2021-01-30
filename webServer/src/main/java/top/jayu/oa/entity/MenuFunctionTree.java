package top.jayu.oa.entity;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class MenuFunctionTree {

    private int functionId;
    private String functionCode;
    private String functionName;
    private List<MenuFunctionTree> children;
    private Object attribute;

    public MenuFunctionTree(int functionId, String functionCode, String functionName) {
        this.functionId = functionId;
        this.functionCode = functionCode;
        this.functionName = functionName;
        this.children = new ArrayList<>();
    }
}
