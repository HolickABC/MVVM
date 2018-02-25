package com.xclib.rxbus;

/**
 * Created by xiongchang on 2017/7/17.
 */

public class RxBusEvent {
    int id;
    String name;
    Object object;
    public RxBusEvent(int id, String name) {
        this.id= id;
        this.name= name;
    }

    public RxBusEvent(int id, String name, Object object) {
        this.id = id;
        this.name = name;
        this.object = object;
    }

    public int getId() {
        return id;
    }
    public String getName() {
        return name;
    }
    public Object getObject() {
        return object;
    }
}
