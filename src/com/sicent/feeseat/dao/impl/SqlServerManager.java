/**
 * 
 */
package com.sicent.feeseat.dao.impl;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * @author wangqiang
 * 
 * @param <T>
 */
public class SqlServerManager<T> {
    private static final Logger log = LoggerFactory.getLogger(SqlServerManager.class);

    @PersistenceContext(unitName = "sqlServer")
    private EntityManager sqlem;

    public T findById(Class<T> c, int id) {
        try {
            return sqlem.find(c, id);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return null;
    }
}
