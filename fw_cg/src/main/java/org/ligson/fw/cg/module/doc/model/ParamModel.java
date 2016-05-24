package org.ligson.fw.cg.module.doc.model;

import org.ligson.fw.core.facade.annotation.Param;

import java.lang.reflect.Field;

/**
 * Created by ligson on 2016/5/24.
 */
public class ParamModel {
    private Field field;
    private Param param;

    public Field getField() {
        return field;
    }

    public void setField(Field field) {
        this.field = field;
    }

    public Param getParam() {
        return param;
    }

    public void setParam(Param param) {
        this.param = param;
    }

    @Override
    public String toString() {
        return "ParamModel{" +
                "field=" + field +
                ", param=" + param +
                '}';
    }

    public ParamModel() {
    }

    public ParamModel(Field field) {
        this.field = field;
        Param param = field.getDeclaredAnnotation(Param.class);
        this.param = param;
    }
}
