package org.ligson.fw.core.event;

/**
 * Created by ligson on 2016/6/24.
 * 系统事件对象
 */
public class Event {
    protected transient Object source;

    public Event(Object source) {
        this.source = source;
    }

    public Object getSource() {
        return source;
    }

    @Override
    public String toString() {
        return "Event{" +
                "source=" + source +
                '}';
    }
}
