package top.jayu.oa.entity;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

import top.jayu.oa.param.BaseParam;

/**
 * 流程步骤
 * 
 * @author yujie
 * @email 402842327@qq.com
 * @date 2021-02-08 08:57:55
 */
@Data
public class OaProcessStep extends BaseParam implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 步骤id（主键）
	 */
	private Integer stepId;
	/**
	 * 步骤名称
	 */
	private String stepName;
	/**
	 * 步骤类型，1：审批 2：条件 3：结束 4：备案
	 */
	private String stepType;

	private Integer stepValue;

	private Float orgPrivLen;
	/**
	 * 条件成功去向
	 */
	private String successTo;
	/**
	 * 条件失败去向
	 */
	private String failTo;
	/**
	 * 创建时间
	 */
	private Date createTime;

}
