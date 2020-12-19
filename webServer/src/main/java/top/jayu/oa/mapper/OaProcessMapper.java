package top.jayu.oa.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import top.jayu.oa.entity.OaProcess;

@Mapper
public interface OaProcessMapper {

    @Select("select * from t_oa_process where bill_type = #{billType} and current_step = #{stepName}")
    OaProcess getProcess(@Param("billType") Byte billType, @Param("stepName") String stepName);

}
