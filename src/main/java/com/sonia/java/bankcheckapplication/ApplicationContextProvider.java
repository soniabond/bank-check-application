package com.sonia.java.bankcheckapplication;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

@Component
public class ApplicationContextProvider implements ApplicationContextAware {


    @Override
    public void setApplicationContext(ApplicationContext applicationContext)
            throws BeansException {
        AppContext.setApplicationContext(applicationContext);
    }

    public static class AppContext{
        private static ApplicationContext ctx;


        public static void setApplicationContext(ApplicationContext applicationContext) {
            ctx = applicationContext;
        }

        public static ApplicationContext getApplicationContext() {
            return ctx;
        }
    }

}
