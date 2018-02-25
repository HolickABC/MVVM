package com.xclib.bindingadapter.swiperefreshlayout;

import android.databinding.BindingAdapter;
import android.support.v4.widget.SwipeRefreshLayout;


/**
 * Created by xiongchang on 2017/10/30.
 */

public class ViewBindingAdapter {

    /**
     * 刷新动画显示与隐藏
     * @param refreshLayout
     * @param isRefresh
     */
    @BindingAdapter("isRefresh")
    public static void isRefresh(SwipeRefreshLayout refreshLayout, boolean isRefresh){
        refreshLayout.setRefreshing(isRefresh);
    }
}
