package org.ligson.fw.cg.module.doc.model;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by ligson on 2016/5/24.
 */
public class ApiCollection {
    private Class clazz;
    private List<ApiModel> apiModelList = new ArrayList<>();

    public Class getClazz() {
        return clazz;
    }

    public void setClazz(Class clazz) {
        this.clazz = clazz;
    }

    public List<ApiModel> getApiModelList() {
        return apiModelList;
    }

    public void setApiModelList(List<ApiModel> apiModelList) {
        this.apiModelList = apiModelList;
    }

    public ApiCollection(Class clazz) {
        this.clazz = clazz;
        Method[] methods = clazz.getDeclaredMethods();
        for (Method method : methods) {
            getApiModelList().add(new ApiModel(method));
        }
    }
}
