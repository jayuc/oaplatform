package top.jayu.oa.mapper;

import org.apache.ibatis.annotations.*;
import top.jayu.oa.entity.UserRole;

import java.util.List;

@Mapper
public interface UserRoleMapper {

    @Insert({
            "<script> ",
            "insert into t_sys_role_user (role_id, user_id) values ",
            "<foreach collection=\"list\" separator=\",\" item=\"item\">",
            " (#{item.roleId}, #{item.userId}) ",
            "</foreach>",
            "</script> "
    })
    Integer batchAdd(@Param("list") List<UserRole> list);

    @Delete("delete from t_sys_role_user where user_id = #{userId}")
    Integer deleteByUserId(Integer userId);

    @Select("select u.*,r.role_name from t_sys_role_user u left join t_sys_role r on u.role_id = r.role_id where u.user_id = #{userId}")
    List<UserRole> getRoleByUserId(Integer userId);

}
