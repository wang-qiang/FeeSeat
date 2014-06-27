/**
 * 
 */
package com.sicent.feeseat.dao;

import com.sicent.feeseat.bean.Obj;

/**
 * 
 * @author wangqiang
 * 
 */
public interface ObjDao extends IBaseDao<Obj> {

    boolean deleteByAreaId(int area_id);

}
