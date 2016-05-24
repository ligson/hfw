package org.ligson.fw.cg.module.doc.model;

import org.ligson.fw.core.facade.base.dto.BaseResponseDto;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by ligson on 2016/5/24.
 */
public class ResponseModel {
    private Class<? extends BaseResponseDto> response;
    private List<ParamModel> paramModels = new ArrayList<>();

    public Class<? extends BaseResponseDto> getResponse() {
        return response;
    }

    public void setResponse(Class<? extends BaseResponseDto> response) {
        this.response = response;
    }

    public List<ParamModel> getParamModels() {
        return paramModels;
    }

    public void setParamModels(List<ParamModel> paramModels) {
        this.paramModels = paramModels;
    }

    public ResponseModel() {
    }

    public ResponseModel(Class<? extends BaseResponseDto> response) {
        this.response = response;
        Field[] fields = response.getDeclaredFields();
        for (Field field : fields) {
            this.getParamModels().add(new ParamModel(field));
        }
    }
}
