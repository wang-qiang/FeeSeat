/**
 * 
 */
package com.sicent.feeseat.dao.impl;

import javax.persistence.Query;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.sicent.feeseat.bean.Obj;
import com.sicent.feeseat.dao.ObjDao;

/**
 * 
 * @author wangqiang
 * 
 */
@Repository("objDao")
public class ObjDaoImpl extends AbstractDaoImpl<Obj> implements ObjDao {

    private static final Logger log = LoggerFactory.getLogger(ObjDaoImpl.class);

    @Override
    public boolean deleteByAreaId(int area_id) {
        try {

            Query nativeQuery = entityManager.createNativeQuery("delete from obj where area_id=?");
            nativeQuery.setParameter(1, area_id);
            nativeQuery.executeUpdate();

            return true;
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return false;
    }

}
