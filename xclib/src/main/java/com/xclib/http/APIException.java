package com.xclib.http;

/**
 * Created by xiongchang on 17/6/27.
 */

public class APIException extends RuntimeException {
    private int code;

    public APIException(String message) {
        super(message);
    }

    public APIException(int code, String message) {
        super(message);
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
}
