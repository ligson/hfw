package org.ligson.fw.core.dao;


import org.ligson.fw.core.common.crud.ICommonQuery;
import org.ligson.fw.core.entity.BasicEntity;

import java.math.BigInteger;

/***
 * hibernate dao封装
 *
 * @param <T>
 */
public interface BaseDao<T extends BasicEntity> extends ICommonQuery<T> {

    /***
     * save操作
     *
     * @param t 实体
     * @return id
     */
    BigInteger add(T t);

    /***
     * save or update 操作
     *
     * @param t 实体
     */
    void saveOrUpdate(T t);

    /***
     * 删除操作
     *
     * @param t 实体
     */
    void delete(T t);

    /***
     * 更新操作
     *
     * @param t 实体
     */
    void update(T t);

    /***
     * 更新某一个属性
     *
     * @param property      属性名
     * @param propertyValue 属性值
     * @param id            实体id
     */
    void updateProperty(String property, String propertyValue, long id);

}