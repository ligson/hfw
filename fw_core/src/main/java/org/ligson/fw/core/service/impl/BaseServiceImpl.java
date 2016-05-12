package org.ligson.fw.core.service.impl;


import org.ligson.fw.core.dao.BaseDao;
import org.ligson.fw.core.entity.BasicEntity;
import org.ligson.fw.core.entity.Pagination;
import org.ligson.fw.core.service.BaseService;

import java.io.Serializable;
import java.util.List;


/**
 * Created by ligson on 2016/4/11.
 */
@SuppressWarnings("unchecked")
public abstract class BaseServiceImpl<T extends BasicEntity> implements BaseService<T> {
    protected BaseDao baseDao;

    public abstract void initBaseDao();

    @Override
    public T get(Serializable id) {
        return (T) baseDao.get(id);
    }

    @Override
    public T findBy(String propertyName, Object propertyValue) {
        return (T) baseDao.findBy(propertyName, propertyValue);
    }

    @Override
    public T findByAnd(T t) {
        return (T) baseDao.findByAnd(t);
    }

    @Override
    public Pagination<T> findAllByEqAnd(T eqT) {
        return baseDao.findAllByEqAnd(eqT);
    }

    @Override
    public Pagination<T> findAllByNeAnd(T neT) {
        return baseDao.findAllByNeAnd(neT);
    }

    @Override
    public Pagination<T> findAllByLkAnd(T lkT) {
        return baseDao.findAllByLkAnd(lkT);
    }

    @Override
    public Pagination<T> findAllByGeAnd(T geT) {
        return baseDao.findAllByGeAnd(geT);
    }

    @Override
    public Pagination<T> findAllByGtAnd(T gtT) {
        return baseDao.findAllByGtAnd(gtT);
    }

    @Override
    public Pagination<T> findAllByLeAnd(T leT) {
        return baseDao.findAllByLeAnd(leT);
    }

    @Override
    public Pagination<T> findAllByLtAnd(T ltT) {
        return baseDao.findAllByLtAnd(ltT);
    }

    @Override
    public Pagination<T> findAllByEqOr(T eqT) {
        return baseDao.findAllByEqOr(eqT);
    }

    @Override
    public Pagination<T> findAllByNeOr(T neT) {
        return baseDao.findAllByNeOr(neT);
    }

    @Override
    public Pagination<T> findAllByLikeOr(T lkT) {
        return baseDao.findAllByLikeOr(lkT);
    }

    @Override
    public Pagination<T> findAllByGeOr(T geT) {
        return baseDao.findAllByGeOr(geT);
    }

    @Override
    public Pagination<T> findAllByGtOr(T gtT) {
        return baseDao.findAllByGtOr(gtT);
    }

    @Override
    public Pagination<T> findAllByLeOr(T leT) {
        return baseDao.findAllByLeOr(leT);
    }

    @Override
    public Pagination<T> findAllByLtOr(T ltT) {
        return baseDao.findAllByLtOr(ltT);
    }

    @Override
    public Pagination<T> findAllByEqAndNe(T eqT, T neT) {
        return baseDao.findAllByEqAndNe(eqT, neT);
    }

    @Override
    public Pagination<T> findAllByEqAndLe(T eqT, T neT) {
        return baseDao.findAllByEqAndLe(eqT, neT);
    }

    @Override
    public Pagination<T> findAllByEqAndLt(T eqT, T neT) {
        return baseDao.findAllByEqAndLt(eqT, neT);
    }

    @Override
    public Pagination<T> findAllByEqAndGe(T eqT, T neT) {
        return baseDao.findAllByEqAndGe(eqT, neT);
    }

    @Override
    public Pagination<T> findAllByEqAndGt(T eqT, T neT) {
        return baseDao.findAllByEqAndGt(eqT, neT);
    }

    @Override
    public Pagination<T> findAllBy(String propertyName, Object propertyValue, T t) {
        return baseDao.findAllBy(propertyName, propertyValue, t);
    }

    @Override
    public long countBy(String propertyName, Object propertyValue) {
        return baseDao.countBy(propertyName, propertyValue);
    }

    @Override
    public long countByAnd(T t) {
        return baseDao.countByAnd(t);
    }

    @Override
    public long countAll() {
        return baseDao.countAll();
    }

    @Override
    public boolean propertyIsUnique(String property, Object propertyValue) {
        return baseDao.propertyIsUnique(property, propertyValue);
    }

    @Override
    public Pagination<T> findAllBy(String propertyName, Object propertyValue) {
        return baseDao.findAllBy(propertyName, propertyValue);
    }

    @Override
    public List<T> list(int offset, int max) {
        return baseDao.list(offset, max);
    }

    @Override
    public List<T> list(int offset, int max, String sort, String order) {
        return baseDao.list(offset, max, sort, order);
    }

    @Override
    public List<T> list() {
        return baseDao.list();
    }

    @Override
    public void add(T t) {
        baseDao.add(t);
    }

    @Override
    public void delete(T t) {
        baseDao.delete(t);
    }

    @Override
    public void update(T t) {
        baseDao.update(t);
    }
}
