package org.ligson.fw.core.facade.web.vo;

import java.io.Serializable;

/**
 * Created by ligson on 2016/4/28.
 * 格式验证无效字段
 */
public class ErrorField implements Serializable {
    private String name;
    private String errorMsg;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    @Override
    public String toString() {
        return "ErrorField{" +
                "name='" + name + '\'' +
                ", errorMsg='" + errorMsg + '\'' +
                '}';
    }

    public ErrorField(String name, String errorMsg) {
        this.name = name;
        this.errorMsg = errorMsg;
    }

    public ErrorField() {
    }
}
