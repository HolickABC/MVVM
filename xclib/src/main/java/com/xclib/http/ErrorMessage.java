package com.xclib.http;

/**
 * Created by xiongchang on 17/6/27.
 */

public class ErrorMessage {

    public static String get(int code) {
        switch (code) {
            case 9999:
                return "系统错误";

            case 10000:
                return "通用错误";

            case 10001:
                return "未登录";

            case 10002:
                return "参数错误";

            case 10003:
                return "无权限";

            case 10004:
                return "找不到对象";

            case 10005:
                return "支付失败";

            case 10006:
                return "密码过于简单";

            case 10007:
                return "token错误";

            case 10008:
                return "需要验证";

            case 10009:
                return "验证码错误";

            case 10010:
                return "访问受限";

            case 10011:
                return "此错误只需要前端把msg里面的内容返回给用户";

            case 10012:
                return "网络连接错误";

            case 10013:
                return "文件错误";

            case 30000:
                return "余额不足";

            default:
                return "服务器错误";
        }
    }
}
