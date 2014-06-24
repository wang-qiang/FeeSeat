package com.sicent.feeseat;

import java.util.List;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import com.sicent.feeseat.bean.Area;
import com.sicent.feeseat.bean.Obj;
import com.sicent.feeseat.bean.TestUsers;

public class DBOperateTest {

    static EntityManagerFactory mysqlFactory;
    static EntityManagerFactory sqlServerFactory;

    @BeforeClass
    public static void beforeClass() {
        mysqlFactory = Persistence.createEntityManagerFactory("mysql");
        sqlServerFactory = Persistence.createEntityManagerFactory("sqlServer");
    }

    @AfterClass
    public static void afterClass() {
        mysqlFactory.close();
        sqlServerFactory.close();
    }

    @Test
    public void testCreateTable() {
    }

    @Test
    public void saveArea() {
        EntityManager em = mysqlFactory.createEntityManager();
        em.getTransaction().begin();

        Area area = new Area();
        area.setBackground("areaBackground");
        area.setDescrip("areaDesc");
        area.setName("areaName");

        Obj obj = null;
        for (int i = 1000; i < 1002; i++) {
            obj = new Obj();
            obj.setName("测试元素" + i);
            obj.setArea(area);
            area.getObjLst().add(obj);
        }

        em.persist(area); // 持久化实体
        em.getTransaction().commit();
        em.close();
    }

    @Test
    public void removeArea() {
        EntityManager em = mysqlFactory.createEntityManager();

        em.getTransaction().begin();
        Area area = em.find(Area.class, 1);

        em.remove(area);

        em.getTransaction().commit();
        em.close();

    }

    @Test
    public void saveObject() {
        EntityManager em = mysqlFactory.createEntityManager();
        em.getTransaction().begin();

        Area area = new Area();
        area.setId(6);

        Obj obj = new Obj();
        obj.setArea(area);
        obj.setDescrip("新增区域图对象");
        obj.setX(53.23f);
        obj.setY(52.245f);
        obj.setHeight(150.24f);
        obj.setWidth(65.4f);

        em.persist(obj); // 持久化实体
        em.getTransaction().commit();
        em.close();
    }

    @Test
    public void removeObject() {
        EntityManager em = mysqlFactory.createEntityManager();

        em.getTransaction().begin();
        Obj obj = em.find(Obj.class, 2);

        // obj.getArea().getObjLst().clear();

        em.remove(obj);

        em.getTransaction().commit();
        em.close();

    }

    @SuppressWarnings("unchecked")
    @Test
    public void findUser2() {
        EntityManager em = sqlServerFactory.createEntityManager();
        Query query = em.createNativeQuery("{call GetUsers()}", TestUsers.class);// 调用存储过程
        List<TestUsers> resultList = query.getResultList();
        System.out.println(resultList);
        em.close();
    }

    @Test
    public void findArea() {
        EntityManager em = mysqlFactory.createEntityManager();
        Area area = em.find(Area.class, 5); // 类似于hibernate的get方法,没找到数据时，返回null
        System.out.println(area.getName());
        final Set<Obj> objLst = area.getObjLst();
        System.out.println(objLst.size());

        for (Obj obj : objLst) {
            System.out.println(obj.getDescrip());
            System.out.println(obj.getY());
        }

        em.close();
    }

    @Test
    public void findObj() {
        EntityManager em = mysqlFactory.createEntityManager();
        Obj obj = em.find(Obj.class, 1); // 类似于hibernate的get方法,没找到数据时，返回null
        System.out.println(obj.getDescrip());
        em.close();
    }

}
