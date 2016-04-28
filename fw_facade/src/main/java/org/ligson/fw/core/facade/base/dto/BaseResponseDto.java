package org.ligson.fw.core.facade.base.dto;


/**
 * 响应实体基础类
 */
@SuppressWarnings("serial")
public class BaseResponseDto extends BaseDto {
    private Boolean success;

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    @Override
    public String toString() {
        return "BaseResponseDto{" +
                "success=" + success +
                '}';
    }
}
