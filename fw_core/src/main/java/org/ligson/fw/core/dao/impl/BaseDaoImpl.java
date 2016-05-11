package org.ligson.fw.core.dao.impl;

import org.apache.commons.collections.CollectionUtils;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.ligson.fw.core.dao.BaseDao;
import org.ligson.fw.core.entity.BasicEntity;
import org.ligson.fw.core.entity.Pagination;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.orm.hibernate5.SessionHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("unchecked")
@Repository("baseDao")
public class BaseDaoImpl<T extends BasicEntity> implements BaseDao<T> {

    private static final Logger logger = LoggerFactory.getLogger(BaseDaoImpl.class);

    @Autowired
    @Qualifier("mySessionFactory")
    private SessionFactory sessionFactory;

    @Transactional("transactionManager")
    @Override
    public BigInteger add(T t) {
        BigInteger id = (BigInteger) currentSession().save(t);
        currentSession().flush();
        return id;
    }

    @Transactional("transactionManager")
    @Override
    public void delete(T t) {
        currentSession().delete(t);
    }

    @Transactional("transactionManager")
    @Override
    public void update(T t) {
        Session session = currentSession();
        session.update(t);
        session.flush();
    }

    @Transactional("transactionManager")
    @Override
    public Pagination<T> findAllBy(String propertyName, Object propertyValue,
                                   T t) {
        String hql;
        if (propertyName == null) {
            hql = "from " + this.getGenericTypeName();
        } else {
            if (propertyValue instanceof String) {

                hql = "from " + this.getGenericTypeName() + " where "
                        + propertyName + "='" + propertyValue + "'";
            } else {

                hql = "from " + this.getGenericTypeName() + " where "
                        + propertyName + "=" + propertyValue;
            }
        }
        return paginationQuery(t, hql);
    }

    @Override
    public Pagination<T> findAllBy(String propertyName, Object propertyValue) {
        try {
            Object obj = getGenericType(0).newInstance();
            Method m = getGenericType(0).getMethod("setPageAble", boolean.class);
            m.invoke(obj, false);
            return findAllBy(propertyName, propertyValue, (T) obj);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Transactional("transactionManager")
    @Override
    public long countBy(String propertyName, Object propertyValue) {
        String hql;
        if (propertyName == null) {
            hql = "select  count(*) from " + this.getGenericTypeName();
            logger.debug("----");
        } else {
            if (propertyValue instanceof String) {
                hql = "select  count(*) from " + this.getGenericTypeName() + " where "
                        + propertyName + "='" + propertyValue + "'";
            } else {
                hql = "select  count(*) from " + this.getGenericTypeName() + " where "
                        + propertyName + "=" + propertyValue;
            }
        }
        Query query = currentSession().createQuery(hql);
        Long count = (Long) query.uniqueResult();
        return count.intValue();
    }

    protected List<String> getNotNullPropNameList(T t) {
        Field[] props = t.getClass().getDeclaredFields();
        List<String> propNames = new ArrayList<String>();
        for (Field field : props) {
            try {
                String getMethodName;
                if (field.getType() == boolean.class) {
                    getMethodName = "is" + field.getName().substring(0, 1).toUpperCase() + field.getName().substring(1);
                } else {
                    getMethodName = "get" + field.getName().substring(0, 1).toUpperCase() + field.getName().substring(1);
                }
                Object value = t.getClass().getDeclaredMethod(getMethodName).invoke(t);
                if (value != null) {
                    propNames.add(field.getName());
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return propNames;
    }

    protected List<Object> getNotNullPropValueList(T t) {
        Field[] props = t.getClass().getDeclaredFields();
        List<Object> propValues = new ArrayList<>();
        for (Field field : props) {
            try {
                String getMethodName;
                if (field.getType() == boolean.class) {
                    getMethodName = "is" + field.getName().substring(0, 1).toUpperCase() + field.getName().substring(1);
                } else {
                    getMethodName = "get" + field.getName().substring(0, 1).toUpperCase() + field.getName().substring(1);
                }
                Object value = t.getClass().getDeclaredMethod(getMethodName).invoke(t);
                if (value != null) {
                    propValues.add(value);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return propValues;
    }

    protected String entityToSql(T t, String and, String relation) {
        List<String> propNames = getNotNullPropNameList(t);
        List<Object> propValues = getNotNullPropValueList(t);
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < propNames.size(); i++) {
            String propName = propNames.get(i);
            Object propValue = propValues.get(i);
            if (propValue instanceof String) {
                builder.append(" ").append(and).append(" ").append(propName).append(relation).append("'").append(propValue).append("'");
            } else {
                builder.append(" ").append(and).append(" ").append(propName).append(relation).append(propValue);
            }
        }
        return builder.toString();
    }

    @Override
    public long countByAnd(T t) {
        String hql = " select count(*) from " + getGenericTypeName() +
                " where 1=1 " + entityToSql(t, "and", "=");
        return (Long) currentSession().createQuery(hql).uniqueResult();
    }

    @Override
    public long countAll() {
        String hql = "select count(*) from " + getGenericTypeName();
        return (Long) currentSession().createQuery(hql).uniqueResult();
    }

    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public Session currentSession() {

        // System.out.println("》》》》》当前线程："+Thread.currentThread().getName()+":"+Thread.currentThread().getId());
        Session session = null;
        try {
            session = getSessionFactory().getCurrentSession();
        } catch (HibernateException exception) {
            //防止当前事务没有绑定session
            Object object = TransactionSynchronizationManager.getResource(getSessionFactory());
            if (object == null) {
                session = getSessionFactory().openSession();
                TransactionSynchronizationManager.bindResource(getSessionFactory(), new SessionHolder(session));
            } else {
                SessionHolder sessionHolder = (SessionHolder) object;
                session = sessionHolder.getSession();
            }
        }
        return session;

    }

    @SuppressWarnings("rawtypes")
    private Class getGenericType(int index) {
        Type genType = getClass().getGenericSuperclass();
        if (!(genType instanceof ParameterizedType)) {
            return Object.class;
        }
        Type[] params = ((ParameterizedType) genType).getActualTypeArguments();
        if (index >= params.length || index < 0) {
            throw new RuntimeException("Index outof bounds");
        }
        if (!(params[index] instanceof Class)) {
            return Object.class;
        }
        return (Class) params[index];
    }

    private String getGenericTypeName() {
        return getGenericType(0).getSimpleName();
    }

    @Transactional("transactionManager")
    @Override
    public T findBy(String propertyName, Object propertyValue) {
        String hql;
        if (propertyValue instanceof String) {
            hql = "from " + this.getGenericTypeName() + " where "
                    + propertyName + "='" + propertyValue + "'";
        } else {

            hql = "from " + this.getGenericTypeName() + " where "
                    + propertyName + "=" + propertyValue;
        }
        Query query = currentSession().createQuery(hql);
        query.setFirstResult(0);
        query.setMaxResults(1);
        List<T> list = query.list();
        if (list.size() > 0) {
            return list.get(0);
        } else {
            return null;
        }
    }

    @Override
    public T findByAnd(T t) {
        String hql = " from " + getGenericTypeName() + " where 1=1 " + entityToSql(t, "and", "=");
        Query query = currentSession().createQuery(hql);
        query.setFirstResult(0);
        query.setMaxResults(1);
        List<T> list = query.list();
        if (CollectionUtils.isNotEmpty(list)) {
            return list.get(0);
        } else {
            return null;
        }
    }

    protected Pagination<T> paginationQuery(T t, String hql) {
        if ((t.getSort() != null) && (t.getOrder() != null)) {
            hql += " order by " + t.getSort() + " " + t.getOrder();
        }
        Query query = currentSession().createQuery(hql);
        Pagination<T> pagination;
        if (t.getPageAble()) {
            query.setFirstResult(t.getOffset());
            query.setMaxResults(t.getMax());
            List<T> list = query.list();
            String countHql = "select count(*) " + hql;
            query = currentSession().createQuery(countHql);
            Long count = (Long) query.uniqueResult();
            pagination = new Pagination<T>(t.getOffset(), t.getMax(), count.intValue(), 0, list);
        } else {
            List<T> list = query.list();
            pagination = new Pagination<>(list);
        }
        return pagination;
    }

    @Override
    public Pagination<T> findAllByEqAnd(T eqT) {
        String hql = " from " + getGenericTypeName() + " where 1=1 " + entityToSql(eqT, "and", "=");
        return paginationQuery(eqT, hql);
    }

    @Override
    public Pagination<T> findAllByNeAnd(T neT) {
        String hql = " from " + getGenericTypeName() + " where 1=1 " + entityToSql(neT, "and", "<>");
        return paginationQuery(neT, hql);
    }

    @Override
    public Pagination<T> findAllByLkAnd(T lkT) {
        String hql = " from " + getGenericTypeName() + " where 1=1 " + entityToSql(lkT, "and", "like");
        return paginationQuery(lkT, hql);
    }

    @Override
    public Pagination<T> findAllByGeAnd(T geT) {
        String hql = " from " + getGenericTypeName() + " where 1=1 " + entityToSql(geT, "and", ">=");
        return paginationQuery(geT, hql);
    }

    @Override
    public Pagination<T> findAllByGtAnd(T gtT) {
        String hql = " from " + getGenericTypeName() + " where 1=1 " + entityToSql(gtT, "and", ">");
        return paginationQuery(gtT, hql);
    }

    @Override
    public Pagination<T> findAllByLeAnd(T leT) {
        String hql = " from " + getGenericTypeName() + " where 1=1 " + entityToSql(leT, "and", "<=");
        return paginationQuery(leT, hql);
    }

    @Override
    public Pagination<T> findAllByLtAnd(T ltT) {
        String hql = " from " + getGenericTypeName() + " where 1=1 " + entityToSql(ltT, "and", "<");
        return paginationQuery(ltT, hql);
    }

    @Override
    public Pagination<T> findAllByEqOr(T eqT) {
        String hql = " from " + getGenericTypeName() + " where 1=1 " + entityToSql(eqT, "or", "=");
        return paginationQuery(eqT, hql);
    }

    @Override
    public Pagination<T> findAllByNeOr(T neT) {
        String hql = " from " + getGenericTypeName() + " where 1=1 " + entityToSql(neT, "or", "<>");
        return paginationQuery(neT, hql);
    }

    @Override
    public Pagination<T> findAllByLikeOr(T lkT) {
        String hql = " from " + getGenericTypeName() + " where 1=1 " + entityToSql(lkT, "or", "like");
        return paginationQuery(lkT, hql);
    }

    @Override
    public Pagination<T> findAllByGeOr(T geT) {
        String hql = " from " + getGenericTypeName() + " where 1=1 " + entityToSql(geT, "or", ">=");
        return paginationQuery(geT, hql);
    }

    @Override
    public Pagination<T> findAllByGtOr(T gtT) {
        String hql = " from " + getGenericTypeName() + " where 1=1 " + entityToSql(gtT, "or", ">");
        return paginationQuery(gtT, hql);
    }

    @Override
    public Pagination<T> findAllByLeOr(T leT) {
        String hql = " from " + getGenericTypeName() + " where 1=1 " + entityToSql(leT, "or", "<=");
        return paginationQuery(leT, hql);
    }

    @Override
    public Pagination<T> findAllByLtOr(T ltT) {
        String hql = " from " + getGenericTypeName() + " where 1=1 " + entityToSql(ltT, "or", "<");
        return paginationQuery(ltT, hql);
    }

    @Override
    public Pagination<T> findAllByEqAndNe(T eqT, T neT) {
        String hql = " from " + getGenericTypeName() + " where (1=1 " + entityToSql(eqT, "and", "=") + ") and (1=1 " + entityToSql(neT, "and", "<>") + ")";
        return paginationQuery(eqT, hql);
    }

    @Override
    public Pagination<T> findAllByEqAndLe(T eqT, T neT) {
        String hql = " from " + getGenericTypeName() + " where (1=1 " + entityToSql(eqT, "and", "=") + ") and (1=1 " + entityToSql(neT, "and", "<=") + ")";
        return paginationQuery(eqT, hql);
    }

    @Override
    public Pagination<T> findAllByEqAndLt(T eqT, T neT) {
        String hql = " from " + getGenericTypeName() + " where (1=1 " + entityToSql(eqT, "and", "=") + ") and (1=1 " + entityToSql(neT, "and", "<") + ")";
        return paginationQuery(eqT, hql);
    }

    @Override
    public Pagination<T> findAllByEqAndGe(T eqT, T neT) {
        String hql = " from " + getGenericTypeName() + " where (1=1 " + entityToSql(eqT, "and", "=") + ") and (1=1 " + entityToSql(neT, "and", ">=") + ")";
        return paginationQuery(eqT, hql);
    }

    @Override
    public Pagination<T> findAllByEqAndGt(T eqT, T neT) {
        String hql = " from " + getGenericTypeName() + " where (1=1 " + entityToSql(eqT, "and", "=") + ") and (1=1 " + entityToSql(neT, "and", ">") + ")";
        return paginationQuery(eqT, hql);
    }

    @Override
    public T get(long id) {
        return (T) currentSession().get(getGenericType(0), id);
    }

    @Transactional("transactionManager")
    @Override
    public void updateProperty(String property, String propertyValue, long id) {
        String hql;
        if (propertyValue instanceof String) {
            hql = "update " + this.getGenericTypeName() + " set " + property
                    + "='" + propertyValue + "' where id='" + id + "'";
        } else {
            hql = "update " + this.getGenericTypeName() + " set " + property
                    + "=" + propertyValue + "' where id='" + id + "'";
        }
        Query query = currentSession().createQuery(hql);
        query.executeUpdate();
    }

    @Override
    public boolean propertyIsUnique(String property, Object propertyValue) {
        // TODO Auto-generated method stub
        long count = countBy(property, propertyValue);
        return count == 0;
    }

    @Override
    public List<T> list(int offset, int max) {
        return list(offset, max, null, null);
    }

    @Override
    public List<T> list(int offset, int max, String sort, String order) {
        String hql = "from " + getGenericTypeName();
        if (sort != null && order != null) {
            hql = hql + " order by " + sort + " " + order;
        }
        Query query = currentSession().createQuery(hql);
        if (offset >= 0 && max >= 0) {
            query.setFirstResult(offset);
            query.setMaxResults(max);
        }
        return query.list();
    }

    @Override
    public List<T> list() {
        return list(-1, -1, null, null);
    }


    @Transactional("transactionManager")
    @Override
    public void saveOrUpdate(T t) {
        currentSession().saveOrUpdate(t);
    }

}