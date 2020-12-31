package top.jayu.oa.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import top.jayu.oa.entity.Code;

import java.util.List;

@Mapper
public interface CodeMapper {

    @Select({
            "<script> ",
            "select * from t_sys_code ",
            " where enable_flag = 1 ",
            "    <if test='code != null and code != 0'> and code = #{code} </if> ",
            "</script> "
    })
    List<Code> list(Code code);

}
