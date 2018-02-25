package com.xclib.loadsir;

/**
 * 数据请求状态图
 */
public enum Status {

    /**
     * 网络加载失败
     */
    NETWORK_ERROR,

    /**
     * 结果为空
     */
    EMPTY,

    /**
     * 加载成功
     */
    SUCCESS,

    /**
     * 加载失败
     */
    ERROR,

    /**
     * 正在加载中
     */
    LOADING
}
