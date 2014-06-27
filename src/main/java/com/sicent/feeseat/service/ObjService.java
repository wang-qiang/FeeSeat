package com.sicent.feeseat.service;

import com.sicent.feeseat.bean.Obj;

public interface ObjService {

    boolean save(Obj obj);

    boolean update(Obj obj);

    Obj findById(Class<Obj> c, Integer objId);

    boolean delete(Integer objId);

    boolean deleteByAreaId(Integer areaId);

}
