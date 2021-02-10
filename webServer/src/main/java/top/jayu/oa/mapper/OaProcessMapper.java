package top.jayu.oa.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import top.jayu.oa.entity.OaProcess;
import top.jayu.oa.entity.ProcessStep;

import java.util.List;

@Mapper
public interface OaProcessMapper {

    @Select("select * from t_oa_process where bill_type = #{billType} and current_step = #{stepName}")
    OaProcess getProcess(@Param("billType") String billType, @Param("stepName") String stepName);

    @Select({
            "<script> ",
            "select * ",
            "  from t_oa_process ",
            "  <where>  ",
            "    <if test='billType != null and billType != 0'> and bill_type = #{billType} </if> ",
            "  </where > ",
            "</script> "
    })
    List<OaProcess> list(OaProcess process);

    @Select("select * from t_oa_process where bill_type = #{billType} and org_priv_len = #{orgPrivLen}")
    List<OaProcess> getProcessByOrgPrivLen(OaProcess process);

    @Select("select p.*,a.success_approve_function_id,a.fail_approve_function_id,a.success_to,a.fail_to from t_oa_process p " +
            "left join t_oa_process_condition a on p.process_condition_id = a.process_condition_id " +
            "where bill_type = #{type}")
    List<ProcessStep> stepProcess(String type);

}
