package com.vianstats.media.convert.config;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author LuoHuan
 * @date 2020/8/24
 */
@Configuration
public class ApplicationHelper implements ApplicationContextAware {

    private ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    @Bean("myApplicationContext")
    public ApplicationContext getApplicationContext() {

        return applicationContext;
    }
}
