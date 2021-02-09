package top.jayu.oa.mapper;

import top.jayu.oa.entity.OaBillCustomerField;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * 自定义流程字段
 * 
 * @author yujie
 * @email 402842327@qq.com
 * @date 2021-02-09 18:31:55
 */
@Mapper
public interface OaBillCustomerFieldMapper {

    @Select({
            "<script> ",
            "select t.* ",
            "  from t_oa_bill_customer_field t ",
            "  <where>  ",

            "  </where > ",
            "</script> "
    })
    List<OaBillCustomerField> list(OaBillCustomerField record);

    @Insert("insert into t_oa_bill_customer_field (" +
            "      bill_type,feild_name,feild_type,feild_value_type,feild_value_value,create_time " +
            "    )values (" +
            "      #{billType},#{feildName},#{feildType},#{feildValueType},#{feildValueValue},now() " +
            "      )")
    Integer add(OaBillCustomerField record);

    @Update({
            "<script> ",
            "update t_oa_bill_customer_field set ",
            "       bill_type = #{billType}, ",
            "       feild_name = #{feildName}, ",
            "       feild_type = #{feildType}, ",
            "       feild_value_type = #{feildValueType}, ",
            "       feild_value_value = #{feildValueValue} ",
            "where customer_field_id = #{customerFieldId}",
            "</script> "
    })
    Integer update(OaBillCustomerField record);

    @Update({
            "<script> ",
            "update t_oa_bill_customer_field ",
            "   <set> ",
            "       <if test='billType != null'> bill_type = #{billType}, </if> ",
            "       <if test='feildName != null'> feild_name = #{feildName}, </if> ",
            "       <if test='feildType != null'> feild_type = #{feildType}, </if> ",
            "       <if test='feildValueType != null'> feild_value_type = #{feildValueType}, </if> ",
            "       <if test='feildValueValue != null'> feild_value_value = #{feildValueValue}, </if> ",
            "   </set> ",
            "where customer_field_id = #{customerFieldId}",
            "</script> "
    })
    Integer updateSelect(OaBillCustomerField record);

    @Delete("delete from t_oa_bill_customer_field where customer_field_id = #{id}")
    Integer delete(Integer id);

    @Delete({
            "<script> ",
            "delete from t_oa_bill_customer_field where customer_field_id in ",
            "   <foreach collection=\"ids\" item=\"item\" index=\"index\" open=\"(\" close=\")\" separator=\",\"> \n" +
            "            #{item}\n" +
            "   </foreach>",
            "</script> "
    })
    Integer batchDelete(List<Integer> ids);
	
}
