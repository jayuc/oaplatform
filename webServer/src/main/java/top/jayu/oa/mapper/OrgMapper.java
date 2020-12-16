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
