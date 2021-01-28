package top.jayu.oa.mapper;

import org.apache.ibatis.annotations.*;
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

    @Insert("insert into t_sys_org (parent_id, org_name, \n" +
            "      short_org_name, org_code, org_code_priv, \n" +
            "      yes_office, yes_leader, leader_id, \n" +
            "      deputy_id, address, tel, \n" +
            "      sort)\n" +
            "    values (#{parentId,jdbcType=INTEGER}, #{orgName,jdbcType=VARCHAR}, \n" +
            "      #{shortOrgName,jdbcType=VARCHAR}, #{orgCode,jdbcType=VARCHAR}, #{orgCodePriv,jdbcType=VARCHAR}, \n" +
            "      #{yesOffice,jdbcType=TINYINT}, #{yesLeader,jdbcType=TINYINT}, #{leaderId,jdbcType=INTEGER}, \n" +
            "      #{deputyId,jdbcType=INTEGER}, #{address,jdbcType=VARCHAR}, #{tel,jdbcType=VARCHAR}, \n" +
            "      #{sort,jdbcType=TINYINT})")
    Integer add(Org record);

    @Update({
            "<script> ",
            "update t_sys_org ",
            "   <set> ",
            "       <if test='parentId != null'> parent_id = #{parentId}, </if> ",
            "       <if test='orgName != null'> org_name = #{orgName}, </if> ",
            "       <if test='shortOrgName != null'> short_org_name = #{shortOrgName}, </if> ",
            "       <if test='orgCode != null'> org_code = #{orgCode}, </if> ",
            "       <if test='orgCodePriv != null'> org_code_priv = #{orgCodePriv}, </if> ",
            "       <if test='leaderId != null'> leader_id = #{leaderId}, </if> ",
            "       <if test='deputyId != null'> deputy_id = #{deputyId}, </if> ",
            "       <if test='address != null'> address = #{address}, </if> ",
            "       <if test='tel != null'> tel = #{tel}, </if> ",
            "   </set> ",
            "where org_id = #{orgId}",
            "</script> "
    })
    Integer update(Org record);

    @Delete("delete from t_sys_org where org_id = #{orgId}")
    Integer delete(Integer orgId);

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
