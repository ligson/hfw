package org.ligson.fw.core.facade.annotation;

import java.lang.annotation.*;

/**
 * Created by ligson on 2015/12/18.
 * 接口描述工具
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Documented
@Retention(RetentionPolicy.RUNTIME)
public @interface Api {

    /***
     * 接口名称
     *
     * @return 名称
     */
    public String name();

    /***
     * 接口描述
     *
     * @return 描述
     */
    public String description() default "";
}

