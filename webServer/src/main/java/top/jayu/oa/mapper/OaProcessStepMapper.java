package top.jayu.oa.mapper;

import top.jayu.oa.entity.OaProcessStep;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * 流程步骤
 * 
 * @author yujie
 * @email 402842327@qq.com
 * @date 2021-02-08 08:57:55
 */
@Mapper
public interface OaProcessStepMapper {

    @Select({
            "<script> ",
            "select t.* ",
            "  from t_oa_process_step t ",
            "  <where>  ",

            "  </where > ",
            "</script> "
    })
    List<OaProcessStep> list(OaProcessStep record);

    @Insert("insert into t_oa_process_step (" +
            "      step_name,step_type,success_to,fail_to,create_time " +
            "    )values (" +
            "      #{stepName},#{stepType},#{successTo},#{failTo},now() " +
            "      )")
    Integer add(OaProcessStep record);

    @Update({
            "<script> ",
            "update t_oa_process_step set ",
            "       step_name = #{stepName}, ",
            "       step_type = #{stepType}, ",
            "       success_to = #{successTo}, ",
            "       fail_to = #{failTo} ",
            "where step_id = #{stepId}",
            "</script> "
    })
    Integer update(OaProcessStep record);

    @Update({
            "<script> ",
            "update t_oa_process_step ",
            "   <set> ",
            "       <if test='stepName != null'> step_name = #{stepName}, </if> ",
            "       <if test='stepType != null'> step_type = #{stepType}, </if> ",
            "       <if test='successTo != null'> success_to = #{successTo}, </if> ",
            "       <if test='failTo != null'> fail_to = #{failTo}, </if> ",
            "   </set> ",
            "where step_id = #{stepId}",
            "</script> "
    })
    Integer updateSelect(OaProcessStep record);

    @Delete("delete from t_oa_process_step where step_id = #{id}")
    Integer delete(Integer id);

    @Delete({
            "<script> ",
            "delete from t_oa_process_step where step_id in ",
            "   <foreach collection=\"ids\" item=\"item\" index=\"index\" open=\"(\" close=\")\" separator=\",\"> \n" +
            "            #{item}\n" +
            "   </foreach>",
            "</script> "
    })
    Integer batchDelete(List<Integer> ids);
	
}
