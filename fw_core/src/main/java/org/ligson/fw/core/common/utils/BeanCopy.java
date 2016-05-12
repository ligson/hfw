package org.ligson.fw.core.common.utils;

import org.ligson.fw.core.common.model.Context;
import org.springframework.beans.BeanUtils;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by ligson on 2016/5/11.
 */
public class BeanCopy {
    /***
     * 原班不动copy
     *
     * @param src
     * @param dest
     */
    public static void copyProperties(Object src, Object dest) {
        BeanUtils.copyProperties(src, dest);
    }

    /***
     * 忽略null属性copy
     *
     * @param src
     * @param dest
     */
    public static void copyPropertiesIgnoreNull(Object src, Object dest) {
        Field[] fields = src.getClass().getDeclaredFields();
        List<String> nullPropList = new ArrayList<>();
        for (Field field : fields) {
            try {
                String getMethod = "get" + field.getName().substring(0, 1).toUpperCase() + field.getName().substring(1);
                Method method = src.getClass().getDeclaredMethod(getMethod);
                Object value = method.invoke(src);
                if (value == null) {
                    nullPropList.add(field.getName());
                }
            } catch (IllegalArgumentException | InvocationTargetException | IllegalAccessException | NoSuchMethodException | SecurityException e) {
                e.printStackTrace();
            }
        }
        String[] nullProps = new String[nullPropList.size()];
        for (int i = 0; i < nullProps.length; i++) {
            nullProps[i] = nullPropList.get(i);
        }
        BeanUtils.copyProperties(src, dest, nullProps);
    }

    public static void main(String[] args) {
        Context context = new Context();
        context.setBusinessDate(new Date());

        Context context1 = new Context();
        copyPropertiesIgnoreNull(context, context1);
        System.out.println(context1.getCurrentDate());
        System.out.println(context1.getBusinessDate());
    }

}
