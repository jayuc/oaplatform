package top.jayu.oa.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import top.jayu.oa.entity.Menu;

import java.util.List;

@Mapper
public interface MenuMapper {

    @Select({
            "<script> ",
            "select * from t_sys_menu ",
            " where enable_flag = 1 ",
            "    <if test='level != null and level != 0'> and level = #{level} </if> ",
            "    <if test='parentCode != null and parentCode != \"\"'> and parent_code = #{parentCode} </if> ",
            " order by sort ",
            "</script> "
    })
    List<Menu> list(Menu record);

    @Select("select * from t_sys_menu where enable_flag = 1 and menu_code = #{code}")
    Menu getByCode(String code);

}
