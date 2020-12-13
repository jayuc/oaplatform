package top.jayu.oa.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import top.jayu.oa.entity.Employ;

@Mapper
public interface EmployMapper {

    @Select("select * from t_sys_employ where login_name = #{loginName}")
    Employ getPasswordByLoginName(String loginName);

}
