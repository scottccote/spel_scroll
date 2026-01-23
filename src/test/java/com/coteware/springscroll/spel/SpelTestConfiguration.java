package com.coteware.springscroll.spel;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.expression.BeanFactoryResolver;

@TestConfiguration
public class SpelTestConfiguration {
    @Bean
    SpelServiceBeanResolver spelServiceBeanResolver(BeanFactory beanFactory) {
        BeanFactoryResolver beanFactoryResolver = new BeanFactoryResolver(beanFactory);
        return new SpelServiceBeanResolver(beanFactoryResolver);
    }

    @Bean(name="spelDemoService") SpelDemoService spelDemoService() {
        return new SpelDemoService();
    }

}
