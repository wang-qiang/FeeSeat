/**
 * 
 */
package com.sicent.feeseat.util;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

import org.springframework.stereotype.Component;

/**
 * SqlServer操作管理
 * 
 * @author wangqiang
 * 
 */

@Component("ssm")
public class SqlServerManager {

    private static EntityManagerFactory emFactory = Persistence
            .createEntityManagerFactory("sqlserver");

    private static EntityManager em = emFactory.createEntityManager();

    public List<?> nativeQuery(String sql) {
        em.getTransaction().begin();

        Query query = em.createNativeQuery(sql);
        List<?> resultList = query.getResultList();

        em.getTransaction().commit();
        return resultList;
    }
}
