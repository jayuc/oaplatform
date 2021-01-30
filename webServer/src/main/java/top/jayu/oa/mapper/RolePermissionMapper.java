package top.jayu.oa.mapper;

import org.apache.ibatis.annotations.*;
import top.jayu.oa.entity.RolePermission;

import java.util.List;

@Mapper
public interface RolePermissionMapper {

    @Insert({
            "<script> ",
            "insert into t_sys_role_permission (role_id, function_id) values ",
            "<foreach collection=\"list\" separator=\",\" item=\"item\">",
            " (#{item.roleId}, #{item.functionId}) ",
            "</foreach>",
            "</script> "
    })
    Integer batchAdd(@Param("list") List<RolePermission> list);

    @Delete("delete from t_sys_role_permission where role_id = #{roleId}")
    Integer deleteByRoleId(Integer roleId);

    @Select({
            "<script> ",
            "select p.*,f.function_code,f.function_name ",
            "  from t_sys_role_permission p left join t_sys_function f on p.function_id = f.function_id ",
            "  <where>  ",
            "    <if test='roleId != null and roleId != 0'> and p.role_id = #{roleId} </if> ",
            "  </where > ",
            "</script> "
    })
    List<RolePermission> list(RolePermission record);

    @Select("select p.function_id,f.function_code,f.function_name from t_sys_role_permission p " +
            "left join t_sys_function f on p.function_id = f.function_id where role_id in\n" +
            "(select role_id from t_sys_role_user where user_id = #{userId})")
    List<RolePermission> getPermissionByUserId(Integer userId);

}
