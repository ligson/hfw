package org.ligson.fw.core.service;


import org.ligson.fw.core.common.crud.ICommonQuery;
import org.ligson.fw.core.entity.BasicEntity;

/**
 * Created by ligson on 2016/4/11.
 */
public interface BaseService<T extends BasicEntity> extends ICommonQuery<T> {
    void add(T t);

    void delete(T t);

    void update(T t);
}
