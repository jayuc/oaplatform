package top.jayu.oa.mapper;

import org.apache.ibatis.annotations.*;
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

    @Select("select * from t_sys_code where code = #{code} and code_no = #{codeNo}")
    Code getCode(Code code);

    @Insert("insert into t_sys_code (code, code_no, name, enable_flag, " +
            "      mark)" +
            "    values (#{code,jdbcType=SMALLINT}, #{codeNo,jdbcType=TINYINT}, #{name,jdbcType=VARCHAR}, " +
            "       #{enableFlag,jdbcType=TINYINT}," +
            "      #{mark,jdbcType=VARCHAR})")
    int add(Code code);

    @Update("update t_sys_code set " +
            "code = #{code,jdbcType=SMALLINT}, " +
            "code_no = #{codeNo,jdbcType=TINYINT}, " +
            "enable_flag = #{enableFlag,jdbcType=TINYINT}, " +
            "name = #{name,jdbcType=VARCHAR}, " +
            "mark = #{mark,jdbcType=VARCHAR} " +
            "where id = #{id}")
    int update(Code code);

    @Delete("delete from t_sys_code where id = #{id}")
    int delete(Code code);

    @Delete({
            "<script> ",
            "delete from t_sys_code where id in ",
            "<foreach collection=\"ids\" item=\"item\" index=\"index\" open=\"(\" close=\")\" separator=\",\"> \n" +
                    "            #{item}\n" +
                    "        </foreach>",
            "</script> "
    })
    int[] batchDelete(List<Integer> ids);

}
