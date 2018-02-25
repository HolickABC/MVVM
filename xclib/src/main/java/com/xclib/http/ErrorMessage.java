package com.xclib.http;

/**
 * Created by xiongchang on 17/6/27.
 */

public class ErrorMessage {
    public static String get(int code) {
        switch (code) {
            case 10003:
                return "密码错误";

            case 10004:
                return "对象不存在";

            case 10005:
                return "用户未登录";

            case 20001:
                return "名称重复";

            case 30002:
                return "验证码错误";

            case 50001:
                return "已加载全部数据";

            case 10000:
                return "内容不合法";

            default:
                return "网络错误";
        }
    }
}
