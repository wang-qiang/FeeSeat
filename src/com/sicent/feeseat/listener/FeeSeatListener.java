/**
 * 
 */
package com.sicent.feeseat.listener;

import java.util.Properties;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import com.sicent.feeseat.util.Util;

/**
 * 
 * @author wangqiang
 * 
 */
public class FeeSeatListener implements ServletContextListener {

    private Properties config = Util.getProperties("config.properties");

    @Override
    public void contextDestroyed(ServletContextEvent arg0) {
    }

    @Override
    public void contextInitialized(ServletContextEvent arg0) {
        System.out.println("=====================================================");
    }

}
