package top.jayu.oa.mapper;

import org.apache.ibatis.annotations.*;
import top.jayu.oa.entity.OaBill;

import java.util.List;

@Mapper
public interface OaBillMapper {

    @Select({
            "<script> ",
            "select * ",
            "  from t_oa_bill ",
            "  <where>  ",
            "    <if test='currentStep != null'> and current_step = #{currentStep} </if> ",
            "  </where > ",
            "</script> "
    })
    List<OaBill> list(OaBill record);

    @Select("select * from t_oa_bill where id = #{id}")
    OaBill getById(Integer id);

    @Insert("insert into t_oa_bill (code, type, \n" +
            "      current_step, stop_flag, employ_id, \n" +
            "      next_employ_id, next_org_id, org_id, \n" +
            "      org_oa_type, start_time, end_time, \n" +
            "      work_age, days, holiday_type, people_number, \n" +
            "      content, address, travel_tool, \n" +
            "      amount, file_url1, file_url2, \n" +
            "      file_url3, extend_content, create_time, \n" +
            "      update_time, mark)\n" +
            "    values (#{code,jdbcType=VARCHAR}, #{type,jdbcType=TINYINT}, \n" +
            "      #{currentStep,jdbcType=VARCHAR}, #{stopFlag,jdbcType=TINYINT}, #{employId,jdbcType=INTEGER}, \n" +
            "      #{nextEmployId,jdbcType=INTEGER}, #{nextOrgId,jdbcType=INTEGER}, #{orgId,jdbcType=INTEGER}, \n" +
            "      #{orgOaType,jdbcType=TINYINT}, #{startTime,jdbcType=TIMESTAMP}, #{endTime,jdbcType=TIMESTAMP}, \n" +
            "      #{workAge,jdbcType=TINYINT}, #{days,jdbcType=SMALLINT}, #{holidayType,jdbcType=SMALLINT}, #{peopleNumber,jdbcType=SMALLINT}, \n" +
            "      #{content,jdbcType=VARCHAR}, #{address,jdbcType=VARCHAR}, #{travelTool,jdbcType=TINYINT}, \n" +
            "      #{amount,jdbcType=INTEGER}, #{fileUrl1,jdbcType=VARCHAR}, #{fileUrl2,jdbcType=VARCHAR}, \n" +
            "      #{fileUrl3,jdbcType=VARCHAR}, #{extendContent,jdbcType=VARCHAR}, now(), \n" +
            "      #{updateTime,jdbcType=TIMESTAMP}, #{mark,jdbcType=VARCHAR})")
    int insert(OaBill record);

    @Delete("delete from t_oa_bill where id = #{id}")
    int deleteById(String id);

    @Update("update t_oa_bill\n" +
            "    set code = #{code,jdbcType=VARCHAR},\n" +
            "      type = #{type,jdbcType=TINYINT},\n" +
            "      current_step = #{currentStep,jdbcType=VARCHAR},\n" +
            "      stop_flag = #{stopFlag,jdbcType=TINYINT},\n" +
            "      employ_id = #{employId,jdbcType=INTEGER},\n" +
            "      next_employ_id = #{nextEmployId,jdbcType=INTEGER},\n" +
            "      next_org_id = #{nextOrgId,jdbcType=INTEGER},\n" +
            "      org_id = #{orgId,jdbcType=INTEGER},\n" +
            "      org_oa_type = #{orgOaType,jdbcType=TINYINT},\n" +
            "      start_time = #{startTime,jdbcType=TIMESTAMP},\n" +
            "      end_time = #{endTime,jdbcType=TIMESTAMP},\n" +
            "      work_age = #{workAge,jdbcType=TINYINT},\n" +
            "      days = #{days,jdbcType=SMALLINT},\n" +
            "      holiday_type = #{holidayType,jdbcType=SMALLINT},\n" +
            "      people_number = #{peopleNumber,jdbcType=SMALLINT},\n" +
            "      content = #{content,jdbcType=VARCHAR},\n" +
            "      address = #{address,jdbcType=VARCHAR},\n" +
            "      travel_tool = #{travelTool,jdbcType=TINYINT},\n" +
            "      amount = #{amount,jdbcType=INTEGER},\n" +
            "      file_url1 = #{fileUrl1,jdbcType=VARCHAR},\n" +
            "      file_url2 = #{fileUrl2,jdbcType=VARCHAR},\n" +
            "      file_url3 = #{fileUrl3,jdbcType=VARCHAR},\n" +
            "      extend_content = #{extendContent,jdbcType=VARCHAR},\n" +
            "      update_time = #{updateTime,jdbcType=TIMESTAMP},\n" +
            "      mark = #{mark,jdbcType=VARCHAR}\n" +
            "    where id = #{id,jdbcType=INTEGER}")
    int update(OaBill record);

}
