package top.jayu.oa.mapper;

import org.apache.ibatis.annotations.*;
import top.jayu.oa.entity.OaAllProcess;

import java.util.List;

@Mapper
public interface OaAllProcessMapper {

    @Select({
            "<script> ",
            "select * from t_oa_all_process ",
            "  <where>  ",
            "    <if test='processName != null and processName != \"\"'> and process_name like concat('%',#{processName},'%') </if> ",
            "  </where > ",
            "</script> "
    })
    List<OaAllProcess> list(OaAllProcess record);

    @Insert("insert into t_oa_all_process (bill_type, process_name, \n" +
            "      process_desc, create_person_id, create_person, \n" +
            "      update_person_id, update_person, create_time, \n" +
            "      update_time, mark)\n" +
            "    values (#{billType,jdbcType=INTEGER}, #{processName,jdbcType=VARCHAR}, \n" +
            "      #{processDesc,jdbcType=VARCHAR}, #{createPersonId,jdbcType=INTEGER}, #{createPerson,jdbcType=VARCHAR}, \n" +
            "      #{updatePersonId,jdbcType=INTEGER}, #{updatePerson,jdbcType=VARCHAR}, now(), \n" +
            "      #{updateTime,jdbcType=TIMESTAMP}, #{mark,jdbcType=VARCHAR})")
    Integer add(OaAllProcess record);

    @Update("update t_oa_all_process\n" +
            "    set bill_type = #{record.billType,jdbcType=INTEGER},\n" +
            "      process_name = #{record.processName,jdbcType=VARCHAR},\n" +
            "      process_desc = #{record.processDesc,jdbcType=VARCHAR},\n" +
            "      update_person_id = #{record.updatePersonId,jdbcType=INTEGER},\n" +
            "      update_person = #{record.updatePerson,jdbcType=VARCHAR},\n" +
            "      update_time = #{record.updateTime,jdbcType=TIMESTAMP},\n" +
            "      mark = #{record.mark,jdbcType=VARCHAR}")
    Integer update(OaAllProcess record);

    @Delete("delete from t_oa_all_process where one_process_id = #{oneProcessId}")
    Integer delete(Integer oneProcessId);

}
