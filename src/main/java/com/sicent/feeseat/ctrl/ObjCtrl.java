/**
 * 
 */
package com.sicent.feeseat.ctrl;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sicent.feeseat.bean.Obj;
import com.sicent.feeseat.service.ObjService;

/**
 * 
 * @author wangqiang
 * 
 */
@Controller
@RequestMapping("obj")
public class ObjCtrl {
    private static final Logger log = LoggerFactory.getLogger(ObjCtrl.class);

    @Autowired
    private ObjService objService;

    /**
     * 保存单个元素
     * 
     * @param obj
     *            需要被保存的元素
     * @return json格式成功或失败
     */
    @RequestMapping(value = "save", method = RequestMethod.POST)
    @ResponseBody
    public Object create(@Valid Obj obj) {
        StringBuffer backMsg = new StringBuffer(32);
        if (obj == null) {
            backMsg.append("{\"success\":false,\"msg\":\"记录不能为空\"}");
            return backMsg;
        }

        boolean isSuccess = objService.save(obj);

        if (isSuccess) {
            backMsg.append("{\"success\":true,\"msg\":\"\"}");
        } else {
            backMsg.append("{\"success\":false,\"msg\":\"保存失败\"}");
        }
        return backMsg;
    }

    /**
     * 更新元素信息
     * 
     * @param obj
     *            需要被更新的元素
     * @return json格式成功或失败
     */
    @RequestMapping(value = "update", method = RequestMethod.POST)
    @ResponseBody
    public Object update(@Valid Obj obj) {
        StringBuffer backMsg = new StringBuffer(32);
        if (obj == null) {
            backMsg.append("{\"success\":false,\"msg\":\"更新记录不存在\"}");
            return backMsg;
        }

        boolean isSuccess = objService.update(obj);
        if (isSuccess) {
            backMsg.append("{\"success\":true,\"msg\":\"\"}");
        } else {
            backMsg.append("{\"success\":false,\"msg\":\"更新失败\"}");
        }
        return backMsg;
    }

    /**
     * 删除区域图上的单个元素
     * 
     * @param objId
     *            元素主键
     * @return json格式成功或失败
     */
    @RequestMapping(value = "delete/{objId}", method = RequestMethod.GET)
    @ResponseBody
    public Object delete(@PathVariable Integer objId) {
        boolean isSuccess = objService.delete(objId);
        if (isSuccess) {
            return "{\"success\":true,\"msg\":\"\"}";
        } else {
            return "{\"success\":false,\"msg\":\"删除失败\"}";
        }
    }

    /**
     * 根据区域id删除所有元素
     * 
     * @param areaId
     *            区域主键
     * @return json格式成功或失败
     */
    @RequestMapping(value = "deletebyarea/{areaId}", method = RequestMethod.GET)
    @ResponseBody
    public Object deleteByArea(@PathVariable Integer areaId) {

        StringBuffer backMsg = new StringBuffer(64);

        boolean isSuccess = objService.deleteByAreaId(areaId);

        if (isSuccess) {
            backMsg.append("{\"success\":true,\"msg\":\"\"}");
        } else {
            backMsg.append("{\"success\":false,\"msg\":\"删除失败\"}");
        }
        return backMsg;
    }
}
