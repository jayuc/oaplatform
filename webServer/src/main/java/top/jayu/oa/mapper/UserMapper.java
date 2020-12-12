package top.jayu.oa.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import top.jayu.oa.entity.User;

@Mapper
public interface UserMapper {

    @Select("select * from t_sys_user where id = #{id}")
    User getUserById(Integer id);

    @Select("select * from t_sys_user where login_name = #{loginName}")
    User getPasswordByLoginName(String loginName);

}
