/**
 * 
 */
package com.sicent.feeseat;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

import org.springframework.stereotype.Component;

/**
 * 
 * @author wangqiang
 * 
 */

@Component("sqlServerManager")
public class SqlServerManager {

    private static EntityManagerFactory emFactory = Persistence
            .createEntityManagerFactory("sqlServer");

    private static EntityManager em = emFactory.createEntityManager();

    public List<?> queryByStoredProcedure(String storedProcedure, Class<?> c) {
        em.getTransaction().begin();

        Query query = em.createNativeQuery(storedProcedure, c);// 调用存储过程
        List<?> resultList = query.getResultList();

        em.getTransaction().commit();

        return resultList;
    }

    private void closeEntityManager() {
        em.close();
    }

    private void closeFactory() {
        closeEntityManager();
        emFactory.close();
    }

    public void close() {
        closeFactory();
    }
}
