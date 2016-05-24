package org.ligson.fw.cg.module.doc.model;

import org.ligson.fw.core.facade.base.dto.BaseRequestDto;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by ligson on 2016/5/24.
 */
public class RequestModel {
    private Class<? extends BaseRequestDto> request;
    private List<ParamModel> paramModels = new ArrayList<>();

    public Class<? extends BaseRequestDto> getRequest() {
        return request;
    }

    public void setRequest(Class<? extends BaseRequestDto> request) {
        this.request = request;
    }

    public List<ParamModel> getParamModels() {
        return paramModels;
    }

    public void setParamModels(List<ParamModel> paramModels) {
        this.paramModels = paramModels;
    }

    @Override
    public String toString() {
        return "RequestModel{" +
                "request=" + request +
                ", paramModels=" + paramModels +
                '}';
    }

    public RequestModel() {
    }

    public RequestModel(Class<? extends BaseRequestDto> request) {
        this.request = request;
        Field[] fields = request.getDeclaredFields();
        for (Field field : fields) {
            this.getParamModels().add(new ParamModel(field));
        }

    }
}
