/**
 * 
 */
package com.sicent.feeseat.ctrl;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.sicent.feeseat.util.SqlServerManager;

@Controller
public class IndexCtrl {

    private static final Logger log = LoggerFactory.getLogger(IndexCtrl.class);

    @Autowired
    private SqlServerManager ssm;

    @RequestMapping("index")
    public ModelAndView index() {
        ModelAndView mv = new ModelAndView("index");
        long start = System.currentTimeMillis();
        /*
         * List<String> list = (List<String>) ssm
         * .nativeQuery("select scom from tstat where snbid='45050110000002'");
         */
        List<?> list = ssm.nativeQuery("{call GetUsers()}");
        log.info("获取到" + list.size() + "条数据，耗时：" + (System.currentTimeMillis() - start) + "ms");

        return mv;
    }

    @RequestMapping(value = "logout")
    public ModelAndView logout(HttpServletRequest request) {
        request.getSession().setAttribute("loginUser", null);
        return index();
    }

    @RequestMapping("404")
    public ModelAndView err404() throws Exception {
        ModelAndView mv = new ModelAndView("err/404");
        return mv;
    }

    @RequestMapping("testException")
    public ModelAndView testException() throws Exception {
        throw new Exception("Sorry , Some error happened!!");
    }

}
