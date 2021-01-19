package top.jayu.oa.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import top.jayu.oa.entity.Org;

import java.util.List;

/**
 * Created by 余杰 on 2020/12/16 10:58
 */

@Mapper
public interface OrgMapper {

    @Select({
            "<script> ",
            "select o.*,u.user_name leader_name,p.user_name deputy_name ",
            "  from t_sys_org o ",
            "  left join t_sys_user u on o.leader_id = u.user_id",
            "  left join t_sys_user p on o.deputy_id = p.user_id",
            "  <where>  ",
            "    <if test='orgName != null and orgName != \"\"'> and o.org_name like concat('%',#{orgName},'%') </if> ",
            "    <if test='orgId != null and orgId != 0'> and o.org_id = #{orgId} </if> ",
            "    <if test='parentId != null and parentId != 0'> and o.parent_id = #{parentId} </if> ",
            "    <if test='orgCodePriv != null and orgCodePriv != \"\"'> and o.org_code like concat(#{orgCodePriv},'%') </if> ",
            "  </where > ",
            "</script> "
    })
    List<Org> list(Org record);

    @Update({
            "<script> ",
            "update t_sys_org ",
            "<set> ",
            "   <if test='leaderId != null'> leader_id = #{leaderId}, </if> ",
            "   <if test='deputyId != null'> deputy_id = #{deputyId}, </if> ",
            "</set> ",
            "where org_id = #{orgId}",
            "</script> "
    })
    Integer update(Org record);

    @Select("select yes_office from t_sys_org where org_id = #{id}")
    Byte ifOffice(Integer id);

    @Select("select leader_id from t_sys_org where org_id = #{id}")
    Integer findOrgLeaderById(Integer id);

    @Select("select o.*,u.user_name deputy_name from t_sys_org o left join t_sys_user u on o.deputy_id = u.user_id " +
            " where o.org_code_priv = #{orgCodePriv}")
    Org findOrgDeputyByPriv(String orgCodePriv);

    @Select("select o.*,u.user_name leader_name from t_sys_org o left join t_sys_user u on o.leader_id = u.user_id " +
            "where o.org_code_priv = #{orgCodePriv}")
    Org findOrgLeaderByPriv(String orgCodePriv);

    @Select("select org_id from t_sys_org where org_code_priv = #{orgCodePriv}")
    Integer getOrgIdByPriv(String orgCodePriv);

    @Select("select * from t_sys_org where org_id = #{id}")
    Org getById(Integer id);

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

    @Select("select o.*,u.user_name deputy_name from t_sys_org o left join t_sys_user u on o.deputy_id = u.user_id " +
            " where length(o.org_code_priv) = 6 and o.org_name like concat(#{firmTypeName},'%')")
    Org getOrgByFirmTypeName(String firmTypeName);

}
