package org.ligson.fw.core.common.crud;


import org.ligson.fw.core.entity.BasicEntity;
import org.ligson.fw.core.entity.Pagination;

import java.util.List;

/**
 * Created by ligson on 2016/4/11.
 */
public interface ICommonQuery<T extends BasicEntity> {
    /***
     * 通过id查找对象
     *
     * @param id id
     * @return 实体
     */
    T get(long id);

    /***
     * 通过一个属性查询
     *
     * @param propertyName  属性名
     * @param propertyValue 属性值
     * @return 实体
     */
    T findBy(String propertyName, Object propertyValue);

    /***
     * 通过多个属性查询一个
     *
     * @param t 查询条件
     * @return 实体
     */
    T findByAnd(T t);

    /***
     * 根据条件查询列表或者分页列表
     *
     * @param eqT 查询条件
     * @return 集合
     */
    Pagination<T> findAllByEqAnd(T eqT);

    Pagination<T> findAllByNeAnd(T neT);

    Pagination<T> findAllByLkAnd(T lkT);

    Pagination<T> findAllByGeAnd(T geT);

    Pagination<T> findAllByGtAnd(T gtT);

    Pagination<T> findAllByLeAnd(T leT);

    Pagination<T> findAllByLtAnd(T ltT);

    Pagination<T> findAllByEqOr(T eqT);

    Pagination<T> findAllByNeOr(T neT);

    Pagination<T> findAllByLikeOr(T lkT);

    Pagination<T> findAllByGeOr(T geT);

    Pagination<T> findAllByGtOr(T gtT);

    Pagination<T> findAllByLeOr(T leT);

    Pagination<T> findAllByLtOr(T ltT);

    /***
     * 根据条件查询列表或者分页列表
     *
     * @param eqT eq条件(分页排序放到这里)
     * @param neT ne条件
     * @return 集合
     */
    Pagination<T> findAllByEqAndNe(T eqT, T neT);

    Pagination<T> findAllByEqAndLe(T eqT, T neT);

    Pagination<T> findAllByEqAndLt(T eqT, T neT);

    Pagination<T> findAllByEqAndGe(T eqT, T neT);

    Pagination<T> findAllByEqAndGt(T eqT, T neT);


    /***
     * 通过一个属性查询列表或者分页列表
     *
     * @param propertyName  属性名
     * @param propertyValue 属性值
     * @param t             分页参数
     * @return 集合
     */
    Pagination<T> findAllBy(String propertyName, Object propertyValue, T t);

    /***
     * 通过一个属性查询列表
     *
     * @param propertyName  属性名
     * @param propertyValue 属性值
     * @return 集合
     */
    Pagination<T> findAllBy(String propertyName, Object propertyValue);


    /***
     * 通过一个属性统计记录数
     *
     * @param propertyName  属性名
     * @param propertyValue 属性值
     * @return 记录数
     */
    long countBy(String propertyName, Object propertyValue);

    /***
     * 通过多个属性统计记录数
     *
     * @param t 条件
     * @return 记录数
     */
    long countByAnd(T t);

    /***
     * 总记录数
     *
     * @return 记录数
     */
    long countAll();

    /***
     * 判断属性是否唯一
     *
     * @param property      属性名
     * @param propertyValue 属性值
     * @return 是否唯一
     */
    boolean propertyIsUnique(String property, Object propertyValue);

    List<T> list(int offset, int max);

    List<T> list(int offset, int max, String sort, String order);

    List<T> list();

}
