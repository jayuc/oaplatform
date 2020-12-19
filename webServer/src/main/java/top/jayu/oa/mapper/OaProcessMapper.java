package top.jayu.oa.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import top.jayu.oa.entity.OaProcess;

@Mapper
public interface OaProcessMapper {

    @Select("select * from t_oa_process where current_step = #{name}")
    OaProcess getStepByName(String name);

}
