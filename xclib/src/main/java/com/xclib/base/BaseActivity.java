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

import com.blankj.utilcode.util.ToastUtils;
import com.gyf.barlibrary.ImmersionBar;
import com.kingja.loadsir.core.LoadService;
import com.xclib.R;
import com.xclib.mvvm.IViewModel;
import com.xclib.mvvm.ViewModelFactory;

import javax.inject.Inject;

import dagger.android.AndroidInjection;

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
    private static int TIME_TO_WAIT = 3 * 1000;

    //状态栏
    protected ImmersionBar mImmersionBar;

    //loadSir状态图
    protected LoadService mLoadService;

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
        //设置DataBinding
        mBinding = DataBindingUtil.setContentView(this, mLayoutResID);
        //初始化状态栏
        if(isImmersionBarEnabled()){
            initImmersionBar();
        }
        getIntentData();
        bindDataBindingAndViewModel();
        if (mViewModel != null) {
            getLifecycle().addObserver((LifecycleObserver) mViewModel);
        }
        initLoadSir();
        initView();
    }

    public ViewModel getViewModel(Class<? extends ViewModel> className, ViewModel viewModel){
        ViewModelFactory factory = new ViewModelFactory(className, viewModel);
        return ViewModelProviders.of(this, factory).get(className);
    }

    //用于初始化DataBinding ViewModel
    protected abstract void bindDataBindingAndViewModel();

    //用于初始化ViewModel等相关操作
    protected abstract void initView();

    //得到界面跳转过来的数值，如果上一个界面有数据传递过来，那么这边需要进行重写该方法
    public void getIntentData() {

    }

    public void initImmersionBar() {
        //在BaseActivity里初始化
        mImmersionBar = ImmersionBar.with(this).statusBarDarkFont(true,0.2f).keyboardEnable(true);
        mImmersionBar.init();
    }

    private boolean isImmersionBarEnabled() {
        return true;
    }

    //界面是否需要loadSir 需要则重写
    public void initLoadSir() {}

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
    }

    /**
     * 返回按钮点击事件，已经封装完毕，非必要，请勿重写
     */
    @Override
    public void onBackPressed() {
        if (mNeedFinishApp) {
            long currentEventTime = System.currentTimeMillis();
            if ((currentEventTime - lastEventTime) > TIME_TO_WAIT) {
                ToastUtils.showShort("请再按一次返回键退出!");
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

    @Override
    public void startActivity(Intent intent) {
        super.startActivity(intent);
        overridePendingTransition(R.anim.activity_up_in, R.anim.activity_up_out);
    }

    @Override
    public void startActivityForResult(Intent intent, int requestCode) {
        super.startActivityForResult(intent, requestCode);
        overridePendingTransition(R.anim.activity_up_in, R.anim.activity_up_out);
    }

    @Override
    public void finish() {
        //推出堆栈
        BaseApplication.getInstance().getActivityManager().popActivity(this);
        super.finish();
        overridePendingTransition(R.anim.activity_down_in, R.anim.activity_down_out);
    }

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
}
