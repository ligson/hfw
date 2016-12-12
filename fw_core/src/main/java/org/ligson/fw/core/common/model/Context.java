package org.ligson.fw.core.common.model;

import org.ligson.fw.core.facade.base.result.Result;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * 上下文对象基类
 */
@SuppressWarnings("unused")
public class Context {

    /**
     * 业务时间
     */
    private Date businessDate;
    /**
     * 返回结果 对象
     */
    private Result result;

    /**
     * 当前业务时间
     */
    private Date currentDate;

    private Map<String, Object> attrMap = new HashMap<String, Object>();

    public void setAttr(String attr, Object value) {
        attrMap.put(attr, value);
    }

    public Object getAttr(String attr) {
        return attrMap.get(attr);
    }

    @SuppressWarnings("unchecked")
    public <T> T getAttr(String attr, Class<T> objCls) {
        Object obj = attrMap.get(attr);
        if (obj == null) {
            return null;
        } else {
            return (T) obj;
        }
    }


    public Set<String> getAttributeNames() {
        return attrMap.keySet();
    }

    public void removeAttr(String attr) {
        attrMap.remove(attr);
    }


    public Date getCurrentDate() {
        return currentDate;
    }

    public void setCurrentDate(Date currentDate) {
        this.currentDate = currentDate;
    }

    public Date getBusinessDate() {
        return businessDate;
    }

    public void setBusinessDate(Date businessDate) {
        this.businessDate = businessDate;
    }

    public Result getResult() {
        return result;
    }

    public void setResult(Result result) {
        this.result = result;
    }

    public Map<String, Object> getAttrMap() {
        return attrMap;
    }

    public void setAttrMap(Map<String, Object> attrMap) {
        this.attrMap = attrMap;
    }

    @Override
    public String toString() {
        return "Context{" +
                "businessDate=" + businessDate +
                ", result=" + result +
                ", currentDate=" + currentDate +
                ", attrMap=" + attrMap +
                '}';
    }
}
