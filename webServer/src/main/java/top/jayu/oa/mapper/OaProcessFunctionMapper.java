package top.jayu.oa.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import top.jayu.oa.entity.OaProcessFunction;

@Mapper
public interface OaProcessFunctionMapper {

    @Select("select * from t_oa_process_function where process_function_id = #{id}")
    OaProcessFunction getById(int id);

}
