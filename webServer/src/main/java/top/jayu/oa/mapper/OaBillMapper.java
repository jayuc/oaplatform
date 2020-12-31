package top.jayu.oa.mapper;

import org.apache.ibatis.annotations.*;
import top.jayu.oa.entity.OaBill;
import top.jayu.oa.param.OaBillParam;

import java.util.List;

@Mapper
public interface OaBillMapper {

    @Select({
            "<script> ",
            "select b.*,u.user_name apply_name ",
            "  from t_oa_bill b",
            "  left join t_sys_user u on b.apply_id = u.user_id ",
            "  <where>  ",
            "    <if test='currentStep != null'> and b.current_step = #{currentStep} </if> ",
            "    <if test='stopFlag != 0'> and b.stop_flag = #{stopFlag} </if> ",
            "    <if test='billType != null'> and b.bill_type = #{billType} </if> ",
            "    <if test='currentUserId != 9999'> and ( b.apply_id = #{currentUserId} or b.next_approve_list like concat('%,', #{currentUserId}, ',%') ) </if> ",
            "  </where > ",
            "</script> "
    })
    List<OaBill> list(OaBill record);

    @Select("select * from t_oa_bill where bill_id = #{id}")
    OaBill getById(Integer id);

    @Insert("insert into t_oa_bill (bill_code, bill_type, \n" +
            "      current_step, stop_flag, apply_id, \n" +
            "      next_approve_list, history_approve_list, apply_org_id, \n" +
            "      history_process_list, apply_org_code_priv, start_time, end_time, \n" +
            "      work_age, days, holiday_type, people_number, \n" +
            "      pass_flag, content, address, travel_tool, \n" +
            "      apply_org_yes_office, amount, file_url1, file_url2, \n" +
            "      firm_type, file_url3, extend_content, create_time, \n" +
            "      people_list, update_time, mark)\n" +
            "    values (#{billCode,jdbcType=VARCHAR}, #{billType,jdbcType=TINYINT}, \n" +
            "      #{currentStep,jdbcType=VARCHAR}, #{stopFlag,jdbcType=TINYINT}, #{applyId,jdbcType=INTEGER}, \n" +
            "      #{nextApproveList,jdbcType=VARCHAR}, #{historyApproveList,jdbcType=VARCHAR}, #{applyOrgId,jdbcType=INTEGER}, \n" +
            "      #{historyProcessList,jdbcType=VARCHAR}, #{applyOrgCodePriv,jdbcType=VARCHAR}, #{startTime,jdbcType=TIMESTAMP}, #{endTime,jdbcType=TIMESTAMP}, \n" +
            "      #{workAge,jdbcType=TINYINT}, #{days,jdbcType=SMALLINT}, #{holidayType,jdbcType=SMALLINT}, #{peopleNumber,jdbcType=SMALLINT}, \n" +
            "      #{passFlag,jdbcType=TINYINT}, #{content,jdbcType=VARCHAR}, #{address,jdbcType=VARCHAR}, #{travelTool,jdbcType=TINYINT}, \n" +
            "      #{applyOrgYesOffice,jdbcType=TINYINT}, #{amount,jdbcType=INTEGER}, #{fileUrl1,jdbcType=VARCHAR}, #{fileUrl2,jdbcType=VARCHAR}, \n" +
            "      #{firmType,jdbcType=TINYINT}, #{fileUrl3,jdbcType=VARCHAR}, #{extendContent,jdbcType=VARCHAR}, now(), \n" +
            "      #{peopleList,jdbcType=VARCHAR}, #{updateTime,jdbcType=TIMESTAMP}, #{mark,jdbcType=VARCHAR})")
    int insert(OaBill record);

    @Delete("delete from t_oa_bill where bill_id = #{id}")
    int deleteById(String id);

    @Update("update t_oa_bill\n" +
            "    set bill_code = #{billCode,jdbcType=VARCHAR},\n" +
            "      bill_type = #{billType,jdbcType=TINYINT},\n" +
            "      current_step = #{currentStep,jdbcType=VARCHAR},\n" +
            "      stop_flag = #{stopFlag,jdbcType=TINYINT},\n" +
            "      pass_flag = #{passFlag,jdbcType=TINYINT},\n" +
            "      apply_id = #{applyId,jdbcType=INTEGER},\n" +
            "      next_approve_list = #{nextApproveList,jdbcType=VARCHAR},\n" +
            "      history_approve_list = #{historyApproveList,jdbcType=VARCHAR},\n" +
            "      apply_org_id = #{applyOrgId,jdbcType=INTEGER},\n" +
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
            "      update_time = now(),\n" +
            "      mark = #{mark,jdbcType=VARCHAR},\n" +
            "      approve_name = #{approveName,jdbcType=VARCHAR},\n" +
            "      approve_id = #{approveId,jdbcType=INTEGER},\n" +
            "      approve_content = #{approveContent,jdbcType=VARCHAR},\n" +
            "      people_list = #{peopleList,jdbcType=VARCHAR},\n" +
            "      history_process_list = #{historyProcessList,jdbcType=VARCHAR}\n" +
            "    where bill_id = #{billId,jdbcType=INTEGER}")
    int update(OaBill record);

    @Update("update t_oa_bill\n" +
            "    set current_step = #{currentStep,jdbcType=VARCHAR},\n" +
            "      stop_flag = #{stopFlag,jdbcType=TINYINT},\n" +
            "      pass_flag = #{passFlag,jdbcType=TINYINT},\n" +
            "      next_approve_list = #{nextApproveList,jdbcType=VARCHAR},\n" +
            "      history_approve_list = #{historyApproveList,jdbcType=VARCHAR},\n" +
            "      approve_org_code_priv = #{approveOrgCodePriv,jdbcType=VARCHAR},\n" +
            "      content = #{content,jdbcType=VARCHAR},\n" +
            "      extend_content = #{extendContent,jdbcType=VARCHAR},\n" +
            "      approve_content = #{approveContent,jdbcType=VARCHAR},\n" +
            "      update_time = now(),\n" +
            "      mark = #{mark,jdbcType=VARCHAR},\n" +
            "      approve_name = #{approveName,jdbcType=VARCHAR},\n" +
            "      approve_id = #{approveId,jdbcType=INTEGER},\n" +
            "      history_process_list = #{historyProcessList,jdbcType=VARCHAR}\n" +
            "    where bill_id = #{billId,jdbcType=INTEGER}")
    int approve(OaBill record);

}
