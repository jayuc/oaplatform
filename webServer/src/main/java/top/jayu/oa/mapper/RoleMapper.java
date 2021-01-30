package top.jayu.oa.mapper;

import org.apache.ibatis.annotations.*;
import top.jayu.oa.entity.Role;

import java.util.List;

/**
 * Created by 余杰 on 2021/1/4 16:43
 */

@Mapper
public interface RoleMapper {

    @Select("select * from t_sys_role")
    List<Role> list(Role role);

    @Insert("insert into t_sys_role (role_name, enable_flag, \n" +
            "      mark)\n" +
            "    values (#{role.roleName}, #{role.enableFlag}, \n" +
            "      #{role.mark})")
    @Options(useGeneratedKeys = true, keyProperty = "role.roleId")
    int add(@Param("role") Role role);

    @Delete("delete from t_sys_role where role_id = #{roleId}")
    int delete(Role role);

    @Update("update t_sys_role set " +
            "role_name = #{roleName,jdbcType=VARCHAR}, " +
            "enable_flag = #{enableFlag,jdbcType=TINYINT}, " +
            "mark = #{mark,jdbcType=VARCHAR} " +
            "where role_id = #{roleId}")
    int update(Role role);

    @Delete({
            "<script> ",
            "delete from t_sys_role where role_id in ",
            "<foreach collection=\"ids\" item=\"item\" index=\"index\" open=\"(\" close=\")\" separator=\",\"> \n" +
                    "            #{item}\n" +
                    "        </foreach>",
            "</script> "
    })
    int batchDelete(List<Integer> ids);

}
