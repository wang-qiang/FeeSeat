package com.sicent.feeseat.service;

import com.sicent.feeseat.bean.Area;

public interface AreaService {

    boolean save(Area area);

    boolean update(Area area);

    Area findById(Class<Area> c, Integer areaId);

    boolean delete(Integer areaId);

    Object queryDetailById(Integer areaId);

}
