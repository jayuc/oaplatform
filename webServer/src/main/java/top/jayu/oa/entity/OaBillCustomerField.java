package top.jayu.oa.entity;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

import top.jayu.oa.param.BaseParam;

/**
 * 自定义流程字段
 * 
 * @author yujie
 * @email 402842327@qq.com
 * @date 2021-02-09 18:31:55
 */
@Data
public class OaBillCustomerField extends BaseParam implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	private Integer customerFieldId;
	/**
	 * 动态表单类别，可与表单类别进行动态匹配
	 */
	private String billType;
	/**
	 * 字段名称
	 */
	private String feildName;
	/**
	 * 字段类型，数据字典：8
	 */
	private String feildType;
	/**
	 * 字段值类型，
	 */
	private String feildValueType;
	/**
	 * 字段值类型的值
	 */
	private String feildValueValue;
	/**
	 * 审批时间
	 */
	private Date createTime;

}
