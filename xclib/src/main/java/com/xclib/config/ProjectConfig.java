package com.xclib.config;

import android.os.Environment;

import java.io.File;

/**
 * Created by xiongchang on 17/7/7.
 */

public class ProjectConfig {
    public static final boolean DEBUG = true;
    public static final String APPNAME = "WXD";

    public static final String DEBUG_BASE_URL = "https://news-at.zhihu.com/";
    public static final String RELEASE_BASE_URL = "http://51wanxiangdai.com/";


    // --------------应用缓存文件基本信息-----------------------
    /**
     * 程序在手机SDK中的主缓存目录.
     */
    public static final String APP_PATH = Environment.getExternalStorageDirectory().getPath() + File.separator
            + APPNAME;
    /**
     * 程序在手机SDK中的缓存目录.
     */
    public static final String DIR_CACHE = APP_PATH + File.separator + "cache/";
    /**
     * 程序在手机SDK中的更新缓存目录.
     */
    public static final String DIR_UPDATE = APP_PATH + File.separator + "update/";
    /**
     * 程序在手机SDK中的图片缓存目录.
     */
    public static final String DIR_IMG = APP_PATH + File.separator + "image/";
    /**
     * 程序在手机SDK中的视频缓存目录.
     */
    public static final String DIR_VIDEO = APP_PATH + File.separator + "video/";
    /**
     * 程序在手机SDK中的音频缓存目录.
     */
    public static final String DIR_AUDIO = APP_PATH + File.separator + "audio/";
    /**
     * 程序在手机SDK中的日志缓存目录.
     */
    public static final String LOGCAT_DIR = APP_PATH + File.separator + "Log/";

    public static String getBaseUrl(){
        if(DEBUG){
            return DEBUG_BASE_URL;
        }else {
            return RELEASE_BASE_URL;
        }
    }
}
