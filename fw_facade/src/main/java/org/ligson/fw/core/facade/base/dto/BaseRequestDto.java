package org.ligson.fw.core.facade.base.dto;

import org.ligson.fw.core.facade.annotation.Param;

/**
 * 请求实体基础类
 */
@SuppressWarnings("serial")
public class BaseRequestDto extends BaseDto {
    /**
     * 版本号
     */
    @Param(name = "版本号", required = false)
    private String version = "0.0.0";
    /**
     * 字符集
     */
    @Param(name = "字符集", required = false)
    private String charset = "UTF-8";

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getCharset() {
        return charset;
    }

    public void setCharset(String charset) {
        this.charset = charset;
    }

    @Override
    public String toString() {
        return "BaseRequestDto{" +
                "version='" + version + '\'' +
                ", charset='" + charset + '\'' +
                '}';
    }
}
