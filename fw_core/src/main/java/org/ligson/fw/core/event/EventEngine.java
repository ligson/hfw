package org.ligson.fw.core.event;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.commons.lang3.Validate;

import org.springframework.util.ReflectionUtils;

public class EventEngine {
    private static final ExecutorService es = Executors.newFixedThreadPool(5);
    private static Map<Class<? extends Event>, List<EventListener<?>>> map = new ConcurrentHashMap<Class<? extends Event>, List<EventListener<?>>>();

    /**
     * 派发事件，同步派发
     *
     * @param event
     */
    public static <T extends Event> void dispatch(T event) {
        dispatch(event, true);
    }

    /**
     * 派发事件
     *
     * @param event 事件
     * @param sync 是否同步
     */
    public static <T extends Event> void dispatch(final T event, boolean sync) {
        if (sync) {
            dispatch0(event);
        } else {
            es.execute(new Runnable() {
                @Override
                public void run() {
                    dispatch0(event);
                }
            });
        }
    }

    private static <T extends Event> void dispatch0(T event) {
        List<EventListener<?>> listeners = map.get(event.getClass());
        if (null != listeners) {
            for (EventListener<?> l : listeners) {
                try {
                    // 直接使用接口方法调用编译不过去，可能是泛型的原因，这里换为反射方式
                    // l.doEvent(event);
                    //l.doEvent(event);
                    //MethodUtils.invokeMethod(l, "doEvent", event);
                    Method method = ReflectionUtils.findMethod(l.getClass(),"doEvent");
                    ReflectionUtils.invokeMethod(method,l,event);
                } catch (Exception e) {
                    String error = e.getMessage();
                    if((error==null)&&(e.getCause()!=null)){
                        error = e.getCause().getMessage();
                    }
                    throw new RuntimeException(error, e);
                }
            }
        }
    }

    public static <T extends Event> void addListener(EventListener<T> listener) {
        Class<T> eventType = getEventType(listener);
        Validate.notNull(eventType, "无法获取到监听器对应的事件类型：" + listener);
        addListener(eventType, listener);
    }

    public static <T extends Event> boolean removeListener(EventListener<T> listener) {
        Class<T> eventType = getEventType(listener);
        Validate.notNull(eventType, "无法获取到监听器对应的事件类型：" + listener);
        return removeListener(eventType, listener);
    }

    private static <T extends Event> boolean removeListener(Class<T> eventType, EventListener<T> listener) {
        List<EventListener<?>> listeners = map.get(eventType);
        if (null == listeners) {
            return false;
        }
        return listeners.remove(listener);
    }

    private static <T extends Event> void addListener(Class<T> eventType, EventListener<T> listener) {
        List<EventListener<?>> listeners = map.get(eventType);
        if (null == listeners) {
            listeners = Collections.synchronizedList(new ArrayList<EventListener<?>>());
            map.put(eventType, listeners);
        }
        listeners.add(listener);
    }

    private static <T extends Event> Class<T> getEventType(EventListener<T> listener) {
        Validate.notNull(listener);
        Type[] types = listener.getClass().getGenericInterfaces();
        for (Type type : types) {
            if (type instanceof ParameterizedType) {
                ParameterizedType ptype = (ParameterizedType) type;
                if (ptype.getRawType() == EventListener.class) {
                    @SuppressWarnings("unchecked")
                    Class<T> eventType = (Class<T>) ptype.getActualTypeArguments()[0];
                    return eventType;
                }
            }
        }
        return null;
    }
}
