package com.xclib.base;

import android.arch.lifecycle.LifecycleObserver;
import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gyf.barlibrary.ImmersionBar;
import com.kingja.loadsir.core.LoadService;
import com.xclib.R;
import com.xclib.mvvm.IViewModel;
import com.xclib.mvvm.ViewModelFactory;

import dagger.android.support.AndroidSupportInjection;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

/**
 * Created by xiongch on 2018/1/4.
 */

public abstract class BaseFragment<DB extends ViewDataBinding, VM extends IViewModel> extends Fragment {

    //ViewDataBinding
    protected DB mBinding;

    protected View mRootView;

    //MVVM ViewModel ViewModelProvider.Factory
    protected ViewModelProvider.Factory mViewModelFactory;

    //ViewModel
    protected VM mViewModel;

    //加载界面的资源id
    protected int mLayoutResID = -1;

    //加载状态图
    protected LoadService mLoadService;

    //rxjava2
    protected CompositeDisposable mCompositeDisposable = new CompositeDisposable();

    //rxBus
    protected Disposable mRxBusDisposable;

    protected BaseFragment(int resId) {
        this.mLayoutResID = resId;
    }

    @Override
    public void onAttach(Context context) {
        //dagger2
        AndroidSupportInjection.inject(this);
        super.onAttach(context);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(inflater,mLayoutResID,container,false);
        mRootView = mBinding.getRoot();
        bindDataBindingAndViewModel();
        initStatusBar();
        if (mViewModel != null) {
            getLifecycle().addObserver((LifecycleObserver) mViewModel);
        }
        initLoadSir(mRootView);
        initRxBus();
        initObservable();
        initView();
        return getFragmentReturnLayout(mRootView);
    }

    protected abstract View getStatusBarView();

    //为View添加状态栏的高度
    private void initStatusBar() {
        if(getStatusBarView() != null){
            ImmersionBar.setTitleBar(getActivity(), getStatusBarView());
        }
    }

    public void initRxBus() {

    }

    //用于初始化LiveData
    public void initObservable() {
    }

    //用于初始化DataBinding ViewModel
    protected abstract void bindDataBindingAndViewModel();

    protected abstract void initView();

    public ViewModel getViewModel(ViewModel viewModel){
        ViewModelFactory factory = new ViewModelFactory(viewModel.getClass(), viewModel);
        return ViewModelProviders.of(this, factory).get(viewModel.getClass());
    }

//    @Override
//    public void startActivity(Intent intent) {
//        super.startActivity(intent);
//        getActivity().overridePendingTransition(R.anim.activity_up_in, R.anim.activity_up_out);
//    }
//
//    @Override
//    public void startActivityForResult(Intent intent, int requestCode) {
//        super.startActivityForResult(intent, requestCode);
//        getActivity().overridePendingTransition(R.anim.activity_up_in, R.anim.activity_up_out);
//    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mBinding = null;
        this.mRootView = null;
        this.mViewModelFactory = null;
        //移除LifecycleObserver
        if (mViewModel != null) {
            getLifecycle().removeObserver((LifecycleObserver) mViewModel);
        }
        this.mViewModel = null;
        //注销RxBus
        if(mRxBusDisposable != null){
            mCompositeDisposable.add(mRxBusDisposable);
        }
        //取消RxJava2订阅 防止内存泄漏
        if(mCompositeDisposable!=null){
            mCompositeDisposable.clear();
            mCompositeDisposable = null;
        }
    }

    //如需要替换为loadSir  则重写该方法
    public void initLoadSir(View mRootView) {

    }

    //如需要替换为loadSir  则重写该方法
    public View getFragmentReturnLayout(View mRootView) {
        return mRootView;
    }
}
