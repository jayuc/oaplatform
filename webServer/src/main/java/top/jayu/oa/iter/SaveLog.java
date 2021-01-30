package top.jayu.oa.iter;

import java.lang.annotation.*;

/**
 * 保存rest请求日志
 * Created by Jay on 2019/11/14 11:49
 */

@Target({ElementType.METHOD,ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface SaveLog {
    /*操作简要说明*/
    String content();
    /*操作详细说明*/
    String description() default "";
    /*操作类型，增(C)、删(D)、改(U)、查(R)、导出(E)、登录(L)、提交任务(J)等*/
    String optionType() default "";
}
