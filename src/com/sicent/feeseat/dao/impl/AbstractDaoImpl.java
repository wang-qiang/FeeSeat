/**
 * 
 */
package com.sicent.feeseat.dao.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

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

    @Transactional
    public boolean save(T t) {
        try {
            entityManager.persist(t);
            return true;
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return false;
    }

    @Transactional
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

    @Transactional
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

    @SuppressWarnings("unchecked")
    public List<Object> nativeQuery(String sql) {
        Query query = entityManager.createNativeQuery(sql);
        return query.getResultList();
    }

    @SuppressWarnings("unchecked")
    public List<Object> hqlQuery(String hql) {
        return entityManager.createQuery(hql).getResultList();
    }

}
