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

import com.sicent.feeseat.SqlServerManager;
import com.sicent.feeseat.bean.TestUsers;

/**
 * 
 * @author wangqiang
 * 
 */
@Controller
public class IndexCtrl {

    private static final Logger log = LoggerFactory.getLogger(IndexCtrl.class);

    @Autowired
    SqlServerManager sqlServerManager;

    @SuppressWarnings("unchecked")
    @RequestMapping("index")
    public ModelAndView index() {
        ModelAndView mv = new ModelAndView("login");
        List<TestUsers> list = (List<TestUsers>) sqlServerManager.queryByStoredProcedure(
                "{call GetUsers()}", TestUsers.class);

        log.info("===========>" + list);
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

}
