/**
 * 
 */
package com.sicent.feeseat.listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 启动监听，做初始化操作等
 * 
 * @author wangqiang
 * 
 */
public class FeeSeatListener implements ServletContextListener {

    private static final Logger log = LoggerFactory.getLogger(FeeSeatListener.class);

    @Override
    public void contextDestroyed(ServletContextEvent arg0) {
    }

    @Override
    public void contextInitialized(ServletContextEvent arg0) {
        log.info("You can do some initialize operation here...");
    }

}
