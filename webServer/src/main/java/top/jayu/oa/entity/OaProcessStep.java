package top.jayu.oa.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 流程步骤表
 * 
 * @author yujie
 * @email 402842327@qq.com
 * @date 2021-02-05 09:56:52
 */
@Data
@TableName("t_oa_process_step")
public class OaProcessStep implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 步骤id（主键）
	 */
	@TableId
	private Integer stepId;
	/**
	 * 步骤名称
	 */
	private String stepName;
	/**
	 * 步骤类型，1：审批 2：条件 3：结束 4：备案
	 */
	private Integer stepType;
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
