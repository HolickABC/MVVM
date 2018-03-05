package com.xclib.bindingadapter.swiperefreshlayout;

import android.databinding.BindingAdapter;
import android.databinding.BindingConversion;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.ColorRes;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;

import com.xclib.R;


/**
 * Created by xiongchang on 2017/10/30.
 */

public class ViewBindingAdapter {

    /**
     * 圈圈的颜色
     *
     * bind:color="@{@color/refresh_color}"
     */
    @BindingAdapter("color")
    public static void color(SwipeRefreshLayout refreshLayout, int color){
        refreshLayout.setColorSchemeColors(color);
    }

    /**
     * 刷新动画显示与隐藏
     * @param refreshLayout
     * @param isRefresh
     */
    @BindingAdapter("isRefresh")
    public static void isRefresh(SwipeRefreshLayout refreshLayout, boolean isRefresh){
        refreshLayout.setRefreshing(isRefresh);
    }

    @BindingAdapter("onRefresh")
    public static void onRefresh(SwipeRefreshLayout refreshLayout, SwipeRefreshLayout.OnRefreshListener listener){
        refreshLayout.setColorSchemeResources(R.color.mainColor);
        refreshLayout.setOnRefreshListener(listener);
    }
}
