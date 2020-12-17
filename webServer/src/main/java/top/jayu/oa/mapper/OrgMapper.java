package top.jayu.oa.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import top.jayu.oa.entity.Org;

import java.util.List;

/**
 * Created by 余杰 on 2020/12/16 10:58
 */

@Mapper
public interface OrgMapper {

    @Select({
            "<script> ",
            "select * ",
            "  from t_sys_org ",
            "  <where>  ",
            "    <if test='orgName != null and orgName != \"\"'> and org_name like concat('%',#{orgName},'%') </if> ",
            "    <if test='orgId != null and orgId != 0'> and org_id = #{orgId} </if> ",
            "    <if test='orgCodePriv != null and orgCodePriv != \"\"'> and org_code like concat(#{orgCodePriv},'%') </if> ",
            "  </where > ",
            "</script> "
    })
    List<Org> list(Org record);

    @Select("select * from t_sys_org where org_id = #{id}")
    Org getById(String id);

    // 一级菜单
    @Select("select * from t_sys_org where parent_id = -1")
    List<Org> list1();

    // 二级菜单
    @Select("select * from t_sys_org where LENGTH (org_code_priv) = 8")
    List<Org> list2();

    // 三级菜单
    @Select("select * from t_sys_org where LENGTH (org_code_priv) = 10")
    List<Org> list3();

    // 四级菜单
    @Select("select * from t_sys_org where LENGTH (org_code_priv) = 12")
    List<Org> list4();

}
