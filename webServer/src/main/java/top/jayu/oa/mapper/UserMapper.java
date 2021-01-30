package top.jayu.oa.mapper;

import org.apache.ibatis.annotations.*;
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

    @Insert("insert into t_sys_user (login_name, password, \n" +
            "      user_name, user_code, employ_id, \n" +
            "      yes_chief, org_id, \n" +
            "      org_code, role_id, tel, mobile_tel, \n" +
            "      position, age, sex) \n" +
            "    values (#{record.loginName,jdbcType=VARCHAR}, #{record.password,jdbcType=VARCHAR}, \n" +
            "      #{record.userName,jdbcType=VARCHAR}, #{record.userCode,jdbcType=VARCHAR}, #{record.employId,jdbcType=INTEGER}, \n" +
            "      #{record.yesChief,jdbcType=TINYINT}, #{record.orgId,jdbcType=INTEGER}, \n" +
            "      #{record.orgCode,jdbcType=VARCHAR}, #{record.roleId,jdbcType=INTEGER}, #{record.tel,jdbcType=VARCHAR}, #{record.mobileTel,jdbcType=VARCHAR}, \n" +
            "       #{record.position,jdbcType=VARCHAR}, #{record.age,jdbcType=INTEGER}, #{record.sex,jdbcType=TINYINT})")
    @Options(useGeneratedKeys = true, keyProperty = "record.userId")
    Integer add(@Param("record") User record);

    @Update({
            "<script> ",
            "update t_sys_user ",
            "<set> ",
            "   <if test='yesChief != null'> yes_chief = #{yesChief}, </if> ",
            "   <if test='loginName != null'> login_name = #{loginName}, </if> ",
            "   <if test='userName != null'> user_name = #{userName}, </if> ",
            "   <if test='userCode != null'> user_code = #{userCode}, </if> ",
            "   <if test='employId != null and employId != 0'> employ_id = #{employId}, </if> ",
            "   <if test='position != null'> position = #{position}, </if> ",
            "   <if test='orgId != null and orgId != 0'> org_id = #{orgId}, </if> ",
            "   <if test='orgCode != null'> org_code = #{orgCode}, </if> ",
            "   <if test='roleId != null and roleId != 0'> role_id = #{roleId}, </if> ",
            "   <if test='tel != null'> tel = #{tel}, </if> ",
            "   <if test='mobileTel != null'> mobile_tel = #{mobileTel}, </if> ",
            "   <if test='age != null and age != 0'> age = #{age}, </if> ",
            "   <if test='sex != null and sex != 0'> sex = #{sex}, </if> ",
            "</set> ",
            "where user_id = #{userId}",
            "</script> "
    })
    Integer update(User record);

    @Update("update t_sys_user set password = #{password} where user_id = #{currentUserId}")
    Integer updatePassword(LoginUser record);

    @Delete("delete from t_sys_user where user_id = #{id}")
    Integer deleteOne(Integer id);

    @Update("update t_sys_user set password = #{password} where user_id = #{userId}")
    Integer resetPassword(LoginUser record);

}
