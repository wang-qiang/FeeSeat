/**
 * 
 */
package com.sicent.feeseat.dao.impl;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sicent.feeseat.dao.IBaseDao;

/**
 * 
 * @author wangqiang
 * 
 * @param <T>
 */
public abstract class AbstractDaoImpl<T> implements IBaseDao<T> {

    private static final String QUERY_ALL = "queryAll";

    private static final Logger log = LoggerFactory.getLogger(AbstractDaoImpl.class);

    @PersistenceContext(unitName = "mysql")
    protected EntityManager entityManager;

    public T findById(Class<T> c, int id) {
        try {
            return entityManager.find(c, id);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return null;
    }

    public boolean save(T t) {
        try {
            entityManager.persist(t);
            return true;
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return false;
    }

    public boolean update(T t) {
        try {
            entityManager.merge(t);
            entityManager.flush();
            return true;
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return false;
    }

    public boolean delete(T t) {
        try {
            entityManager.remove(entityManager.contains(t) ? t : entityManager.merge(t));
            return true;
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return false;
    }

    @SuppressWarnings("unchecked")
    public List<T> queryAll() {
        return entityManager.createNamedQuery(QUERY_ALL).getResultList();
    }

    @SuppressWarnings("unchecked")
    public List<T> queryEntity(String hql) {
        return entityManager.createQuery(hql).getResultList();
    }

    /**
     * 调用本地sql执行查询操作，使用preparestatement防注入
     */
    @SuppressWarnings("unchecked")
    public List<Object> nativeQuery(String sql, HashMap<Integer, Object> params) {

        Query query = entityManager.createNativeQuery(sql);
        if (params != null && !params.isEmpty()) {
            Set<Entry<Integer, Object>> entrySet = params.entrySet();
            Iterator<Entry<Integer, Object>> iterator = entrySet.iterator();
            while (iterator.hasNext()) {
                Entry<Integer, Object> next = iterator.next();
                query.setParameter(next.getKey(), next.getValue());
            }
        }
        return query.getResultList();
    }

    /**
     * 调用本地sql执行更新、删除等
     */
    public int nativeUpdate(String sql, HashMap<Integer, Object> params) {

        Query query = entityManager.createNativeQuery(sql);
        if (params != null && !params.isEmpty()) {
            Set<Entry<Integer, Object>> entrySet = params.entrySet();
            Iterator<Entry<Integer, Object>> iterator = entrySet.iterator();
            while (iterator.hasNext()) {
                Entry<Integer, Object> next = iterator.next();
                query.setParameter(next.getKey(), next.getValue());
            }
        }
        return query.executeUpdate();
    }

    @SuppressWarnings("unchecked")
    public List<Object> hqlQuery(String hql) {
        return entityManager.createQuery(hql).getResultList();
    }

}
