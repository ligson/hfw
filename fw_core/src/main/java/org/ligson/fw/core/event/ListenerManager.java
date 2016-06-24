package org.ligson.fw.core.event;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.ListableBeanFactory;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

/**
 * Created by trq on 2016/6/24.
 */
@Lazy(false)
@Component
public class ListenerManager implements InitializingBean, BeanFactoryAware {
    private ListableBeanFactory beanFactory;

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        this.beanFactory = (ListableBeanFactory) beanFactory;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        for (EventListener<?> listener : beanFactory.getBeansOfType(EventListener.class).values()) {
            EventEngine.addListener(listener);
        }
    }
}
