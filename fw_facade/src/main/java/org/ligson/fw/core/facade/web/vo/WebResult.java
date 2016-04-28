package org.ligson.fw.core.facade.web.vo;


import org.ligson.fw.core.facade.base.result.Result;

import java.util.HashMap;

/**
 * Created by ligson on 2016/3/17.
 * json返回对象
 */
public class WebResult extends HashMap<String, Object> {
    private Boolean success = false;
    private String errorCode;
    private String errorMsg;

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
        if (success != null) {
            put("success", success);
        }
    }

    @Override
    public void clear() {
        super.clear();
        setSuccess(false);
        setErrorCode(null);
        setErrorMsg(null);
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
        if (errorCode != null) {
            put("errorCode", errorCode);
        }
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
        if (errorMsg != null) {
            put("errorMsg", errorMsg);
        }
    }

    public void setError(Result errorResult) {
        setSuccess(false);
        setErrorMsg(errorResult.getFailureMessage());
        setErrorCode(errorResult.getFailureCode());
    }
}
