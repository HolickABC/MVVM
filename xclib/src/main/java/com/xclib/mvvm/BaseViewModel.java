package com.xclib.mvvm;

import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleObserver;
import android.arch.lifecycle.OnLifecycleEvent;
import android.arch.lifecycle.ViewModel;
import android.databinding.ObservableField;

import com.xclib.loadsir.Status;

import io.reactivex.disposables.CompositeDisposable;

/**
 * Created by xiongch on 2018/1/4.
 */

public class BaseViewModel extends ViewModel implements IViewModel, LifecycleObserver {

    protected CompositeDisposable mCompositeDisposable = new CompositeDisposable();

    /**
     * 数据请求状态图loadSir
     */
    public ObservableField<Status> status = new ObservableField<>(Status.LOADING);

    //注册RxBus等
    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    @Override
    public void onStart() {

    }

    //销毁操作  如反注册RxBus
    @Override
    protected void onCleared() {
        if(mCompositeDisposable!=null){
            mCompositeDisposable.clear();
            mCompositeDisposable = null;
        }
        super.onCleared();
    }

    @Override
    public void showNetworkErrorView() {
        status.set(Status.NETWORK_ERROR);
    }

    @Override
    public void showEmptyView() {
        status.set(Status.EMPTY);
    }

    @Override
    public void showSuccessfulView() {
        status.set(Status.SUCCESS);
    }

    @Override
    public void showErrorView() {
        status.set(Status.ERROR);
    }

}
