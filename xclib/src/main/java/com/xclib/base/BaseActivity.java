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
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.blankj.utilcode.util.DeviceUtils;
import com.blankj.utilcode.util.KeyboardUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.gyf.barlibrary.ImmersionBar;
import com.jude.swipbackhelper.SwipeBackHelper;
import com.jude.swipbackhelper.SwipeBackPage;
import com.kingja.loadsir.core.LoadService;
import com.xclib.R;
import com.xclib.http.SchedulerTransformer;
import com.xclib.mvvm.IViewModel;
import com.xclib.mvvm.ViewModelFactory;
import com.xclib.rxbus.RxBus;
import com.xclib.toast.ToastHelper;
import com.xclib.util.MyLogUtil;

import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import dagger.android.AndroidInjection;
import io.reactivex.Observable;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.observers.DisposableObserver;

/**
 * Created by xiongch on 2018/1/4.
 */

public abstract class BaseActivity<DB extends ViewDataBinding, VM extends IViewModel> extends AppCompatActivity {

    //ViewDataBinding
    protected DB mBinding;

    //MVVM ViewModel ViewModelProvider.Factory
    protected ViewModelProvider.Factory mViewModelFactory;

    //ViewModel
    protected VM mViewModel;

    //加载界面的资源id
    protected int mLayoutResID = -1;

    //是否需要关闭app
    protected boolean mNeedFinishApp = false;

    //最后一次返回按钮操作事件
    private long lastEventTime;

    //推出app的等待时间
    private int TIME_TO_WAIT = 3 * 1000;

    //状态栏
    protected ImmersionBar mImmersionBar;

    //loadSir状态图
    protected LoadService mLoadService;

    //rxjava2
    protected CompositeDisposable mCompositeDisposable = new CompositeDisposable();

    //SwipeBack
    protected SwipeBackPage mSwipeBackPage;

    //rxBus
    protected Disposable mRxBusDisposable;

    public BaseActivity(int layoutResID) {
        this.mLayoutResID = layoutResID;
    }

    public BaseActivity(int layoutResID, boolean needFinishApp) {
        this.mLayoutResID = layoutResID;
        this.mNeedFinishApp = needFinishApp;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        //Dagger2
        AndroidInjection.inject(this);
        super.onCreate(savedInstanceState);
        //加入堆栈
        BaseApplication.getInstance().getActivityManager().pushActivity(this);
        if(isSwipeBackHelperEnable()){
            SwipeBackHelper.onCreate(this);
            initSwipeBackHelper();
        }
        //设置DataBinding
        mBinding = DataBindingUtil.setContentView(this, mLayoutResID);
        getIntentData();
        bindDataBindingAndViewModel();
        //初始化状态栏
        if(isImmersionBarEnabled()){
            initImmersionBar();
            initStatusBar();
        }
        if (mViewModel != null) {
            getLifecycle().addObserver((LifecycleObserver) mViewModel);
        }
        initLoadSir();
        initRxBus();
        initObservable();
        initView();
    }

    protected abstract View getStatusBarView();

    //为View添加状态栏的高度
    private void initStatusBar() {
        if(getStatusBarView() != null){
            ImmersionBar.setTitleBar(this, getStatusBarView());
        }
    }

    //用于监听LiveData
    public void initObservable() {
    }

    public ViewModel getViewModel(ViewModel viewModel){
        ViewModelFactory factory = new ViewModelFactory(viewModel.getClass(), viewModel);
        return ViewModelProviders.of(this, factory).get(viewModel.getClass());
    }

    //用于初始化DataBinding ViewModel
    protected abstract void bindDataBindingAndViewModel();

    //用于初始化ViewModel等相关操作
    protected abstract void initView();

    //得到界面跳转过来的数值，如果上一个界面有数据传递过来，那么这边需要进行重写该方法
    public void getIntentData() {

    }

    public void initImmersionBar() {
        //注意： keyboardEnable会导致在7.0中将app缩小时键盘展示在下方   界面多了个键盘高度
        mImmersionBar = ImmersionBar.with(this).statusBarDarkFont(true,0.2f).keyboardEnable(true);
        mImmersionBar.init();
    }

    private boolean isImmersionBarEnabled() {
        return true;
    }

    //界面是否需要loadSir 需要则重写
    public void initLoadSir() {}

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        if(isSwipeBackHelperEnable()){
            SwipeBackHelper.onPostCreate(this);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        this.mBinding = null;
        this.mViewModelFactory = null;
        //移除LifecycleObserver
        if (mViewModel != null) {
            getLifecycle().removeObserver((LifecycleObserver) mViewModel);
        }
        this.mViewModel = null;
        //在BaseActivity里销毁
        if (mImmersionBar != null){
            mImmersionBar.destroy();
        }
        //注销RxBus
        if(mRxBusDisposable != null){
            mCompositeDisposable.add(mRxBusDisposable);
        }
        //取消RxJava2订阅 防止内存泄漏
        if(mCompositeDisposable!=null){
            mCompositeDisposable.clear();
            mCompositeDisposable = null;
        }
        if(isSwipeBackHelperEnable()){
            SwipeBackHelper.onDestroy(this);
        }
    }

    /**
     * 关闭界面，重写了系统的方法，在里面增加了系统Activity堆栈管理功能
     */
    @Override
    public void finish() {
        //这里再次推出出栈是无奈之举  勿删  因为返回两下退出app时  调用finish()不会走onDestroy()
        // 因为 android.os.Process.killProcess(android.os.Process.myPid())把程序杀死了  finish()必须调用
        BaseApplication.getInstance().getActivityManager().popActivity(this);
        super.finish();
    }

    /**
     * 返回按钮点击事件，已经封装完毕，非必要，请勿重写
     */
    @Override
    public void onBackPressed() {
        if (mNeedFinishApp) {
            long currentEventTime = System.currentTimeMillis();
            if ((currentEventTime - lastEventTime) > TIME_TO_WAIT) {
                ToastHelper.showShort("请再按一次返回键退出!");
                lastEventTime = currentEventTime;
            } else {
                BaseApplication.getInstance().getActivityManager().popAllActivity();
                android.os.Process.killProcess(android.os.Process.myPid());
                Runtime.getRuntime().gc();
            }
        } else {
            super.onBackPressed();
        }
    }

//    @Override
//    public void startActivity(Intent intent) {
//        super.startActivity(intent);
//        overridePendingTransition(R.anim.activity_up_in, R.anim.activity_up_out);
//    }
//
//    @Override
//    public void startActivityForResult(Intent intent, int requestCode) {
//        super.startActivityForResult(intent, requestCode);
//        overridePendingTransition(R.anim.activity_up_in, R.anim.activity_up_out);
//    }
//
//    @Override
//    public void finish() {
//        super.finish();
//        overridePendingTransition(R.anim.activity_down_in, R.anim.activity_down_out);
//    }

    /**
     * 事件的分发
     * @param ev
     * @return
     */
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            View v = getCurrentFocus();
            if (isShouldHideInput(v, ev)) {

                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                if (imm != null) {
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                }
            }
            return super.dispatchTouchEvent(ev);
        }
        // 必不可少，否则所有的组件都不会有TouchEvent了
        if (getWindow().superDispatchTouchEvent(ev)) {
            return true;
        }
        return onTouchEvent(ev);
    }

    /**
     * 判断软键盘是否需要隐藏
     * @param v
     * @param event
     * @return
     */
    public boolean isShouldHideInput(View v, MotionEvent event) {
        if (v != null && (v instanceof EditText)) {
            int[] leftTop = { 0, 0 };
            //获取输入框当前的location位置
            v.getLocationInWindow(leftTop);
            int left = leftTop[0];
            int top = leftTop[1];
            int bottom = top + v.getHeight();
            int right = left + v.getWidth();
            if (event.getX() > left && event.getX() < right
                    && event.getY() > top && event.getY() < bottom) {
                // 点击的是输入框区域，保留点击EditText的事件
                return false;
            } else {
                return true;
            }
        }
        return false;
    }

    //显示键盘
    public void showKeyBoard(View view){
        Disposable keyBoardDisposable = Observable.timer(150, TimeUnit.MILLISECONDS)
                .compose(new SchedulerTransformer<Long>())
                .subscribe(new Consumer<Long>() {
                    @Override
                    public void accept(Long aLong) throws Exception {
                        KeyboardUtils.showSoftInput(view);
                    }
                });
        if(mCompositeDisposable != null){
            mCompositeDisposable.add(keyBoardDisposable);
        }
    }

    private boolean isSwipeBackHelperEnable() {
        return true;
    }

    public void initSwipeBackHelper() {
        mSwipeBackPage = SwipeBackHelper.getCurrentPage(this)//获取当前页面
                .setSwipeBackEnable(true)//设置是否可滑动
                .setSwipeEdge(200)//可滑动的范围。px。200表示为左边200px的屏幕
                .setSwipeEdgePercent(0.2f)//可滑动的范围。百分比。0.2表示为左边20%的屏幕
                .setSwipeSensitivity(0.5f)//对横向滑动手势的敏感程度。0为迟钝 1为敏感
                .setClosePercent(0.8f)//触发关闭Activity百分比
                .setSwipeRelateEnable(true)//是否与下一级activity联动(微信效果)。默认关
                .setSwipeRelateOffset(400)//activity联动时的偏移量。默认500px。
                .setDisallowInterceptTouchEvent(false);//不抢占事件，默认关（事件将先由子View处理再由滑动关闭处理）
    }

    //注册rxBus
    public void initRxBus() {

    }
}
