/**
 * 
 */
package com.sicent.feeseat.ctrl;

import javax.servlet.http.HttpServletRequest;

import org.codehaus.jackson.map.util.JSONPObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.sicent.feeseat.bean.Area;
import com.sicent.feeseat.service.AreaService;

/**
 * 
 * @author wangqiang
 * 
 */
@Controller
@RequestMapping("area")
public class AreaCtrl {
    private static final Logger log = LoggerFactory.getLogger(AreaCtrl.class);

    @Autowired
    private AreaService areaService;

    /**
     * 
     * @return
     */
    @RequestMapping(value = "new", method = RequestMethod.GET)
    @ResponseBody
    public Object newArea() {
        Area area = new Area();
        area.setName("区域图-" + System.currentTimeMillis());
        return area;
    }

    /**
     * 
     * @param request
     * @return
     */
    @RequestMapping(value = "new", method = RequestMethod.POST)
    @ResponseBody
    public Object save(HttpServletRequest request) {
        String data = request.getParameter("data");
        Area area = JSON.parseObject(data, Area.class);
        if (area != null) {
            if (areaService.save(area)) {
                return "{\"success\":true,\"msg\":\"\"}";
            }
        }
        return "{\"success\":false,\"msg\":\"保存失败\"}";
    }

    /**
     * 更新机位图
     * 
     * @param request
     * @return
     */
    @RequestMapping(value = "update", method = RequestMethod.POST)
    @ResponseBody
    public Object update(HttpServletRequest request) {
        String data = request.getParameter("data");
        Area area = JSON.parseObject(data, Area.class);
        if (area == null) {
            return "{\"success\":false,\"msg\":\"更新失败，更新数据格式错误\"}";
        }

        Area oldArea = areaService.findById(Area.class, area.getId());
        if (oldArea == null) {
            return "{\"success\":false,\"msg\":\"更新失败，机位图已被删除\"}";
        }
        if (areaService.update(area)) {
            return "{\"success\":true,\"msg\":\"\"}";
        }
        return "{\"success\":false,\"msg\":\"更新失败\"}";
    }

    /**
     * 根据机位图id获取机位图数据
     * 
     * @param areaId
     * @return
     */
    @RequestMapping(value = "edit/{areaId}", method = RequestMethod.GET)
    @ResponseBody
    public Object edit(@PathVariable Integer areaId) {
        // Area area = areaService.findById(Area.class, areaId);
        // return area;

        return areaService.queryDetailById(areaId);
    }

    /**
     * 跨域访问接口
     * 
     * @param areaId
     * @param jsoncallback
     * @param request
     * @return
     */
    @RequestMapping(value = "show/{areaId}", method = RequestMethod.GET)
    @ResponseBody
    public JSONPObject show(@PathVariable Integer areaId, @RequestParam String jsoncallback) {
        Area area = areaService.findById(Area.class, areaId);

        return new JSONPObject(jsoncallback, area);
    }

    /**
     * 删除机位图
     * 
     * @param areaId
     * @return
     */
    @RequestMapping(value = "delete/{areaId}", method = RequestMethod.GET)
    @ResponseBody
    public Object delete(@PathVariable Integer areaId) {
        boolean isSuccess = areaService.delete(areaId);
        if (isSuccess) {
            return "{\"success\":true,\"msg\":\"\"}";
        } else {
            return "{\"success\":false,\"msg\":\"删除失败\"}";
        }
    }
}
