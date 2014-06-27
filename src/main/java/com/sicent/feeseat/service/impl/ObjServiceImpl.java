package com.sicent.feeseat.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.sicent.feeseat.bean.Area;
import com.sicent.feeseat.bean.Obj;
import com.sicent.feeseat.dao.AreaDao;
import com.sicent.feeseat.dao.ObjDao;
import com.sicent.feeseat.handler.FeeSeatException;
import com.sicent.feeseat.service.ObjService;

@Repository("objService")
public class ObjServiceImpl implements ObjService {
    // private static final Logger log = LoggerFactory.getLogger(ObjServiceImpl.class);

    @Autowired
    private ObjDao objDao;

    @Autowired
    private AreaDao areaDao;

    @Override
    @Transactional
    public boolean save(Obj obj) {
        return objDao.save(obj);
    }

    @Override
    @Transactional
    public boolean update(Obj obj) {
        return objDao.update(obj);
    }

    @Override
    public Obj findById(Class<Obj> c, Integer objId) {
        return objDao.findById(c, objId);
    }

    @Override
    @Transactional
    public boolean delete(Integer objId) {
        Obj obj = objDao.findById(Obj.class, objId);
        if (obj == null) {
            return false;
        }

        // 移除引用
        obj.getArea().getObjLst().clear();
        return objDao.delete(obj);
    }

    @Override
    @Transactional
    public boolean deleteByAreaId(Integer areaId) {
        boolean success = objDao.deleteByAreaId(areaId);
        if (success) {
            Area area = areaDao.findById(Area.class, areaId);
            area.setDetail("");
            boolean state = areaDao.update(area);
            if (!state) {
                throw new FeeSeatException("删除area表id为[" + areaId + "]时出错");
            }
        }
        return success;
    }

}
