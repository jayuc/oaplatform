package top.jayu.oa.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import top.jayu.oa.entity.OaProcessCondition;

@Mapper
public interface OaProcessConditionMapper {

    @Select("select * from t_oa_process_condition where process_condition_id = #{id}")
    OaProcessCondition getConditionById(int id);

}
