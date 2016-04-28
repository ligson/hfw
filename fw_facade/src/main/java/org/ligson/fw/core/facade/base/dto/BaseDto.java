package org.ligson.fw.core.facade.base.dto;

import org.ligson.fw.core.facade.annotation.Param;

import java.io.Serializable;

/**
 * 基础类
 */
@SuppressWarnings("serial")
public class BaseDto implements Serializable {

    /**
     * 扩展字段
     */
    @Param(name = "扩展字段", required = false)
    private String extParam;

    @Param(name = "token", required = false)
    private String token;

    public String getExtParam() {
        return extParam;
    }

    public void setExtParam(String extParam) {
        this.extParam = extParam;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @Override
    public String toString() {
        return "BaseDto [extParam=" + extParam + ", token=" + token + ", toString()=" + super.toString() + "]";
    }

}
