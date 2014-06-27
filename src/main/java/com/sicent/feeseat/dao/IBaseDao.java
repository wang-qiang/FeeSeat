/**
 * 
 */
package com.sicent.feeseat.dao;

import java.util.HashMap;
import java.util.List;

/**
 * 
 * @author wangqiang
 * 
 * @param <T>
 */
public interface IBaseDao<T> {

    /**
     * 查询全部
     * 
     * @return
     */
    List<T> queryAll();

    /**
     * 查询对象
     * 
     * @param hql
     * @return
     */
    List<T> queryEntity(String hql);

    /**
     * 根据自己需要，使用hql查询某些字段
     * 
     * @param hql
     * @return
     */
    List<Object> hqlQuery(String hql);

    /**
     * 根据自己需要，使用原生sql查询
     * 
     * @param sql
     * @return
     */
    List<Object> nativeQuery(String sql, HashMap<Integer, Object> params);

    /**
     * 根据自己需要，使用原生sql更新
     * 
     * @param sql
     * @param params
     * @return
     */
    int nativeUpdate(String sql, HashMap<Integer, Object> params);

    /**
     * 根据id查询记录
     * 
     * @param c
     * @param id
     * @return
     */
    T findById(Class<T> c, int id);

    /**
     * 保存记录
     * 
     * @param t
     * @return
     */
    boolean save(T t);

    /**
     * 更新记录
     * 
     * @param t
     * @return
     */
    boolean update(T t);

    /**
     * 删除记录
     * 
     * @param t
     * @return
     */
    boolean delete(T t);

}
