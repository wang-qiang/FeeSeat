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
import com.sicent.feeseat.dao.AreaDao;
import com.sicent.feeseat.util.Util;

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
    private AreaDao areaDao;

    @RequestMapping(value = "new", method = RequestMethod.GET)
    @ResponseBody
    public Object newArea() {
        Area area = new Area();
        area.setName("区域图-" + System.currentTimeMillis());
        return area;
    }

    @RequestMapping(value = "new", method = RequestMethod.POST)
    @ResponseBody
    public Object save(HttpServletRequest request) {
        String data = request.getParameter("data");

        log.info("fetch data : " + data);

        Area area = JSON.parseObject(data, Area.class);
        if (area != null) {

            boolean isSuccess = areaDao.save(area);
            if (isSuccess) {
                log.info("============>" + area);
            } else {
                log.error("error----->" + area);
            }
        }

        return area;
    }

    @RequestMapping(value = "edit/{areaId}", method = RequestMethod.GET)
    @ResponseBody
    public Object edit(@PathVariable Integer areaId) {
        Area area = areaDao.findById(Area.class, areaId);
        return area;
    }

    @RequestMapping(value = "show/{areaId}", method = RequestMethod.GET)
    @ResponseBody
    public JSONPObject show(@PathVariable Integer areaId, @RequestParam String jsoncallback,
            HttpServletRequest request) {
        Area area = areaDao.findById(Area.class, areaId);
        String userIp = Util.getUserIp(request);
        String hostIp = Util.getHostIp();
        if ("0:0:0:0:0:0:0:1".equals(userIp) || "localhost".equals(userIp)
                || (Util.isEmpty(hostIp) && hostIp.equals(userIp))) {
            return new JSONPObject("", area);
        }

        return new JSONPObject(jsoncallback, area);
    }

    @RequestMapping(value = "delete/{areaId}", method = RequestMethod.GET)
    @ResponseBody
    public Object delete(@PathVariable Integer areaId) {

        log.debug("Began to delete area[id=" + areaId + "]");
        StringBuffer backMsg = new StringBuffer(32);
        Area area = areaDao.findById(Area.class, areaId);
        if (area == null) {
            backMsg.append("{\"success\":false,\"msg\":\"删除失败，没有相关记录\"}");
            return backMsg;
        }

        boolean isSuccess = areaDao.delete(area);
        if (isSuccess) {
            backMsg.append("{\"success\":true,\"msg\":\"\"}");
        } else {
            backMsg.append("{\"success\":false,\"msg\":\"删除失败\"}");
        }
        return backMsg;
    }
}
