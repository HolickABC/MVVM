package com.xclib.http;

import java.io.Serializable;

/**
 * Created by xiongchang on 17/6/27.
 */

public class BaseModel<T> implements Serializable {

    public String msg;
    public int code;
    public T data;

    public boolean success(){
        //成功的状态码标识
        return code == 0;
    }

}
