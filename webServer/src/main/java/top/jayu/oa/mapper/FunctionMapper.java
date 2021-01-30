package top.jayu.oa.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import top.jayu.oa.entity.Function;

import java.util.List;

@Mapper
public interface FunctionMapper {

    @Select({
            "<script> ",
            "select * from t_sys_function ",
            " where enable_flag = 1 ",
            "    <if test='menuCode != null and menuCode != \"\"'> and menu_code = #{menuCode} </if> ",
            "</script> "
    })
    List<Function> list(Function record);

}
