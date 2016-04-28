package org.ligson.fw.core.facade.annotation;

import java.lang.annotation.*;

/**
 * Created by ligson on 2016/3/30.
 * 请求参数
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Param {
    /***
     * 参数名
     */
    String name();

    /***
     * 参数说明
     */
    String comment() default "";

    /***
     * 是否必备
     */
    boolean required();

    /***
     * 是否是邮箱
     */
    boolean email() default false;

    /***
     * 是否是手机号
     */
    boolean mobile() default false;

    /***
     * 最大值
     */
    int max() default -1;

    /***
     * 最小值
     */
    int min() default -1;

    /***
     * 正则表达式
     */
    String regexp() default "";

    /***
     * 最大长度
     */
    int maxLen() default -1;

    /***
     * 最小长度
     */
    int minLen() default -1;

    /***
     * 是否是整数
     */
    boolean integer() default false;

    /***
     * 集合
     */
    String[] inList() default {};

}
