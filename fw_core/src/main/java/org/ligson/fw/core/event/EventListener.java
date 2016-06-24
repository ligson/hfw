package org.ligson.fw.core.event;

/**
 * Created by ligson on 2016/6/24.
 * 事件监听器
 */
public interface EventListener<E extends Event> {
    void doEvent(E e);
}
