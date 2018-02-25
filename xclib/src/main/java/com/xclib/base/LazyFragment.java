package com.xclib.base;

import android.databinding.ViewDataBinding;
import android.os.Bundle;

import com.xclib.mvvm.IViewModel;

/**
 * Created by xiongchang on 17/7/7.
 */

public abstract class LazyFragment<VB extends ViewDataBinding, VM extends IViewModel> extends BaseFragment<VB, VM> {

    protected boolean isViewInitiated; //控件是否初始化完成
    protected boolean isVisibleToUser; //页面是否可见
    protected boolean isDataInitiated; //数据是否加载

    protected LazyFragment(int resId) {
        super(resId);
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        this.isVisibleToUser = isVisibleToUser;
        prepareFetchData(false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        isViewInitiated = true;
        prepareFetchData(false);
    }

    public abstract void loadData();


    protected void prepareFetchData(boolean forceUpdate) {
        if (isVisibleToUser && isViewInitiated && (!isDataInitiated || forceUpdate)) {
            loadData();
            isDataInitiated = true;
        }
    }
}
