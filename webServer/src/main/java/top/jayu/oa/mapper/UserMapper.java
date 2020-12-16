package top.jayu.oa.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import top.jayu.oa.entity.User;

import java.util.List;

@Mapper
public interface UserMapper {

    @Select("select * from t_sys_user where user_id = #{id}")
    User getUserById(Integer id);

    @Select("select * from t_sys_user where login_name = #{loginName}")
    User getPasswordByLoginName(String loginName);

    @Select("select * from t_sys_user")
    List<User> list();

}
