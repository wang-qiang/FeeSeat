/**
 * 
 */
package com.sicent.feeseat.dao.impl;

import javax.persistence.Query;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.sicent.feeseat.bean.Area;
import com.sicent.feeseat.bean.Obj;
import com.sicent.feeseat.dao.AreaDao;

/**
 * 
 * @author wangqiang
 * 
 */
@Repository("areaDao")
public class AreaDaoImpl extends AbstractDaoImpl<Area> implements AreaDao {

    private static final Logger log = LoggerFactory.getLogger(AreaDaoImpl.class);

    @Override
    public boolean save(Area area) {
        try {

            entityManager.persist(area);
            StringBuffer objIds = new StringBuffer(32);
            for (Obj obj : area.getObjLst()) {
                objIds.append(obj.getId() + ",");
            }

            String condition = "(";
            if (objIds.length() > 1) {
                condition += objIds.substring(0, objIds.length() - 1);
            }
            condition += ")";
            Query nativeQuery = entityManager.createNativeQuery("update obj set area_id="
                    + area.getId() + " where id in " + condition);
            nativeQuery.executeUpdate();

            // 将该机位图数据同步到area detail字段
            area.setDetail(null);

            return true;
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return false;
    }

    @Override
    public boolean update(Area area) {
        try {
            entityManager.merge(area);
            entityManager.flush();
            return true;
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return false;
    }

}
