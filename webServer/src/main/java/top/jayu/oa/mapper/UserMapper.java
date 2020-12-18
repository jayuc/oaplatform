package top.jayu.oa.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import top.jayu.oa.entity.User;

import java.util.List;

@Mapper
public interface UserMapper {

    @Select("select * from t_sys_user where user_id = #{id}")
    User getUserById(Integer id);

    @Select("select yes_chief from t_sys_user where user_id = #{id}")
    Byte ifChief(int id);

    @Select("select password from t_sys_user where login_name = #{loginName}")
    String getPasswordByLoginName(String loginName);

    @Select("select * from t_sys_user where login_name = #{loginName}")
    User getUserByLoginName(String loginName);

    @Select({
            "<script> ",
            "select * ",
            "  from t_sys_user ",
            "  <where>  ",
            "    <if test='userName != null and userName != \"\"'> and user_name like concat('%',#{userName},'%') </if> ",
            "    <if test='orgId != null and orgId != 0'> and org_id = #{orgId} </if> ",
            "    <if test='orgCodePriv != null and orgCodePriv != \"\"'> and org_code like concat(#{orgCodePriv},'%') </if> ",
            "  </where > ",
            "</script> "
    })
    List<User> list(User record);

}
