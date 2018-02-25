package com.xclib.http;

import java.io.Serializable;

/**
 * Created by xiongchang on 17/6/27.
 */

public class BaseModel<T> implements Serializable {

    public int state;
    public T data;

    public boolean sucess(){
        //成功的状态码标识
        return state == 0;
    }

}
