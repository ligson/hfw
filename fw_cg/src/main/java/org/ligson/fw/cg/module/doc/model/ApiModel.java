package org.ligson.fw.cg.module.doc.model;

import org.ligson.fw.core.facade.annotation.Api;
import org.ligson.fw.core.facade.base.dto.BaseRequestDto;

import java.lang.reflect.Method;

/**
 * Created by ligson on 2016/5/24.
 */
public class ApiModel {
    private Method method;
    private Api api;
    private RequestModel requestDto;
    private ResponseModel responseDto;

    public Method getMethod() {
        return method;
    }

    public void setMethod(Method method) {
        this.method = method;
    }

    public Api getApi() {
        return api;
    }

    public void setApi(Api api) {
        this.api = api;
    }

    public RequestModel getRequestDto() {
        return requestDto;
    }

    public void setRequestDto(RequestModel requestDto) {
        this.requestDto = requestDto;
    }

    public ResponseModel getResponseDto() {
        return responseDto;
    }

    public void setResponseDto(ResponseModel responseDto) {
        this.responseDto = responseDto;
    }

    public ApiModel() {
    }

    public ApiModel(Method method) {
        this.method = method;
        Api api = method.getDeclaredAnnotation(Api.class);
        this.api = api;
        Class[] params = method.getParameterTypes();
        Class param = params[0];
        Class<? extends BaseRequestDto> paramClazz = param;
        requestDto = new RequestModel(paramClazz);
        Class returnType = method.getReturnType();
        responseDto = new ResponseModel(returnType);
    }
}
