package top.jayu.oa.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import top.jayu.oa.entity.User;
import top.jayu.oa.param.LoginUser;

import java.util.List;

@Mapper
public interface UserMapper {

    @Select("select * from t_sys_user where user_id = #{id}")
    User getUserById(Integer id);

    @Select("select yes_chief from t_sys_user where user_id = #{id}")
    Byte ifChief(int id);

    @Select("select password from t_sys_user where login_name = #{loginName}")
    String getPasswordByLoginName(String loginName);

    @Select("select u.*,o.org_name,o.org_code_priv,o.yes_office " +
            "from t_sys_user u " +
            "left join t_sys_org o on u.org_id = o.org_id " +
            "where u.login_name = #{loginName}")
    User getUserByLoginName(String loginName);

    @Select({
            "<script> ",
            "select * ",
            "  from t_sys_user ",
            "  <where>  ",
            "    <if test='userName != null and userName != \"\"'> and user_name like concat('%',#{userName},'%') </if> ",
            "    <if test='orgId != null and orgId != 0'> and org_id = #{orgId} </if> ",
            "    <if test='orgCode != null and orgCode != \"\"'> and org_code = #{orgCode} </if> ",
            "    <if test='orgCodePriv != null and orgCodePriv != \"\"'> and org_code like concat(#{orgCodePriv},'%') </if> ",
            "  </where > ",
            "</script> "
    })
    List<User> list(User record);

    @Update({
            "<script> ",
            "update t_sys_user ",
            "<set> ",
            "   <if test='yesChief != null'> yes_chief = #{yesChief}, </if> ",
            "</set> ",
            "where user_id = #{userId}",
            "</script> "
    })
    Integer update(User record);

    @Update("update t_sys_user set password = #{password} where user_id = #{currentUserId}")
    Integer updatePassword(LoginUser record);

}
