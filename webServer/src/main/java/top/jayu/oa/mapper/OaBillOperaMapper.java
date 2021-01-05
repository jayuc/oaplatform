package top.jayu.oa.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import top.jayu.oa.entity.OaBillOpera;

import java.util.List;

@Mapper
public interface OaBillOperaMapper {

    @Insert("insert into t_oa_bill_opera (bill_opera_id, bill_id, bill_code, bill_type, \n" +
            "      bill_step, bill_step_name, opera_id, opera_name, \n" +
            "      opera_org_id, opera_org_name, pass_flag, content, create_time\n" +
            "      )\n" +
            "    values (#{billOperaId,jdbcType=INTEGER}, #{billId,jdbcType=INTEGER}, #{billCode,jdbcType=VARCHAR}, #{billType,jdbcType=TINYINT}, \n" +
            "      #{billStep,jdbcType=VARCHAR}, #{billStepName,jdbcType=VARCHAR}, #{operaId,jdbcType=INTEGER}, #{operaName,jdbcType=VARCHAR}, \n" +
            "      #{operaOrgId,jdbcType=INTEGER}, #{operaOrgName,jdbcType=VARCHAR}, #{passFlag,jdbcType=TINYINT}, #{content,jdbcType=VARCHAR}, now()\n" +
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
