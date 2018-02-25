package com.xclib.util;

import android.util.Log;

import com.xclib.config.ProjectConfig;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;


/**
 * 日志工具类
 */
public class MyLogUtil {
    //注意修改
    private static boolean isLogEnable = ProjectConfig.DEBUG;

    private static boolean isFileEnable = true;

    private static boolean isVerboseEnable = true;

    private static boolean isDebugEnable = true;

    private static boolean isInfoEnable = true;

    private static boolean isWarnEnable = true;

    private static boolean isErrorEnable = true;

    private static final String MYLOGFILEName = "Log.txt";

    private static final SimpleDateFormat myLogSdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINA);

    private static final SimpleDateFormat logfile = new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA);

    public static void v(String tag, String message) {
        v(tag, message, null);
    }

    public static void v(String tag, String message, Throwable tr) {
        if (isVerboseEnable()) {
            if (null != tr) {
                Log.v(tag, message, tr);
            } else {
                Log.v(tag, message);
            }
            if (isFileEnable) {
                writeLogtoFile("v", tag, message);
            }
        }
    }

    public static void d(String tag, String message) {
        d(tag, message, null);
    }

    public static void d(String tag, String message, Throwable tr) {
        if (isDebugEnable()) {
            if (null != tr) {
                Log.d(tag, message, tr);
            } else {
                Log.d(tag, message);
            }
            if (isFileEnable) {
                writeLogtoFile("d", tag, message);
            }
        }
    }

    public static void i(String tag, String message) {
        i(tag, message, null);
    }

    public static void i(String tag, String message, Throwable tr) {
        if (isInfoEnable()) {
            if (null != tr) {
                Log.i(tag, message, tr);
            } else {
                Log.i(tag, message);
            }
            if (isFileEnable) {
                writeLogtoFile("i", tag, message);
            }
        }
    }

    public static void w(String tag, String message) {
        w(tag, message, null);
    }

    public static void w(String tag, String message, Throwable tr) {
        if (isWarnEnable()) {
            if (null != tr) {
                Log.w(tag, message, tr);
            } else {
                Log.w(tag, message);
            }
            if (isFileEnable) {
                writeLogtoFile("w", tag, message);
            }
        }
    }

    public static void e(String tag, String message) {
        e(tag, message, null);
    }

    public static void e(String tag, String message, Throwable tr) {
        if (isErrorEnable()) {
            if (null != tr) {
                Log.e(tag, message, tr);
            } else {
                Log.e(tag, message);
            }
            if (isFileEnable) {
                writeLogtoFile("e", tag, message);
            }
        }
    }

    private static boolean isVerboseEnable() {
        return isLogEnable && isVerboseEnable;
    }

    private static boolean isDebugEnable() {
        return isLogEnable && isDebugEnable;
    }

    private static boolean isInfoEnable() {
        return isLogEnable && isInfoEnable;
    }

    private static boolean isWarnEnable() {
        return isLogEnable && isWarnEnable;
    }

    private static boolean isErrorEnable() {
        return isLogEnable && isErrorEnable;
    }

    private static void writeLogtoFile(String mylogtype, String tag, String text) {
        Date nowtime = new Date();
        String needWriteFiel = logfile.format(nowtime);
        String needWriteMessage = myLogSdf.format(nowtime) + "    " + mylogtype + "    " + tag + "    " + text;
        File file = new File(ProjectConfig.LOGCAT_DIR, needWriteFiel + MYLOGFILEName);
        try {
            FileWriter filerWriter = new FileWriter(file, true);
            BufferedWriter bufWriter = new BufferedWriter(filerWriter);
            bufWriter.write(needWriteMessage);
            bufWriter.newLine();
            bufWriter.close();
            filerWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
