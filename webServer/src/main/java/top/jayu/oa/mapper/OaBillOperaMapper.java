package top.jayu.oa.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import top.jayu.oa.entity.OaBillOpera;

import java.util.List;

@Mapper
public interface OaBillOperaMapper {

    @Insert("insert into t_oa_bill_opera (bill_opera_id, bill_code, bill_type, \n" +
            "      bill_step, opera_id, \n" +
            "      pass_flag, content, create_time\n" +
            "      )\n" +
            "    values (#{billOperaId,jdbcType=INTEGER}, #{billCode,jdbcType=VARCHAR}, #{billType,jdbcType=TINYINT}, \n" +
            "      #{billStep,jdbcType=VARCHAR}, #{operaId,jdbcType=INTEGER}, \n" +
            "      #{passFlag,jdbcType=TINYINT}, #{content,jdbcType=VARCHAR}, now()\n" +
            "      )")
    int insert(OaBillOpera record);

    @Select({
            "<script> ",
            "select * from t_oa_bill_opera ",
            "  <where>  ",
            "    <if test='beginTime != null and endTime != null'> and create_time between #{beginTime} and #{endTime} </if> ",
            "  </where > ",
            "</script> "
    })
    List<OaBillOpera> list(OaBillOpera record);

}
