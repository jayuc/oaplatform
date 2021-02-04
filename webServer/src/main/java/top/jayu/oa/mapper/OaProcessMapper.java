package top.jayu.oa.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import top.jayu.oa.entity.OaProcess;

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

}
