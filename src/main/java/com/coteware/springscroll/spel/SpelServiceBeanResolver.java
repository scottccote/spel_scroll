package com.coteware.springscroll.spel;

import org.springframework.context.expression.BeanFactoryResolver;
import org.springframework.expression.AccessException;
import org.springframework.expression.BeanResolver;
import org.springframework.expression.EvaluationContext;

public class SpelServiceBeanResolver implements BeanResolver {

    private final BeanFactoryResolver beanFactoryResolver;

    public SpelServiceBeanResolver(BeanFactoryResolver beanFactoryResolver) {
        this.beanFactoryResolver = beanFactoryResolver;
    }

    @Override
    public Object resolve(EvaluationContext context, String beanName) throws AccessException {
        System.err.println("Trying to find the bean with name [" + beanName + "]");
        return this.beanFactoryResolver.resolve(context,beanName);
    }
}
