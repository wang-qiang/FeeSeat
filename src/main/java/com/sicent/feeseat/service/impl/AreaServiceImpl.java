/**
 * 
 */
package com.sicent.feeseat.service.impl;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.sicent.feeseat.bean.Area;
import com.sicent.feeseat.dao.AreaDao;
import com.sicent.feeseat.dao.ObjDao;
import com.sicent.feeseat.handler.FeeSeatException;
import com.sicent.feeseat.service.AreaService;

/**
 * 
 * @author wangqiang
 * 
 */
@Repository("areaService")
public class AreaServiceImpl implements AreaService {

    @Autowired
    private AreaDao areaDao;

    @Autowired
    private ObjDao objDao;

    @Override
    @Transactional
    public boolean save(Area area) {
        return areaDao.save(area);
    }

    @Override
    @Transactional
    public boolean update(Area area) {
        // 删除原数据
        boolean success = objDao.deleteByAreaId(area.getId());

        if (!success) {
            throw new FeeSeatException("更新Area[id=" + area.getId() + "]，删除原数据错误！");
        }

        // 更新数据
        boolean update = areaDao.update(area);

        if (update) {

            // 设置新增加的元素父级
            String sql = "update obj set area_id=? where area_id is Null";
            HashMap<Integer, Object> params = new HashMap<Integer, Object>(1);
            params.put(1, area.getId());
            try {
                objDao.nativeUpdate(sql, params);
            } catch (Exception e) {
                throw new FeeSeatException("更新Area[id=" + area.getId() + "]，添加新数据错误！");
            }

            // 更新父级detail
            Area newArea = areaDao.findById(Area.class, area.getId());
            newArea.setDetail(null);

            // throw new FeeSeatException("更新Area[id=" + area.getId() + "]，添加新数据错误！");
            return true;
        }
        return false;
    }

    @Override
    @Transactional
    public Object queryDetailById(Integer areaId) {

        HashMap<Integer, Object> params = new HashMap<Integer, Object>(1);
        params.put(1, areaId);

        String sql = "select detail from area where id=?";

        List<Object> query = areaDao.nativeQuery(sql, params);
        if (query.isEmpty()) {
            return "";
        }
        return query.get(0);
    }

    @Override
    @Transactional
    public Area findById(Class<Area> c, Integer areaId) {
        return areaDao.findById(c, areaId);
    }

    @Override
    @Transactional
    public boolean delete(Integer areaId) {
        Area area = areaDao.findById(Area.class, areaId);
        if (area == null) {
            return true;
        }
        return areaDao.delete(area);
    }

}
