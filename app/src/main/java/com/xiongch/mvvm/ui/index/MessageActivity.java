package com.xiongch.mvvm.ui.index;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.gyf.barlibrary.ImmersionBar;
import com.xclib.base.BaseActivity;
import com.xiongch.mvvm.R;
import com.xiongch.mvvm.databinding.ActivityMessageBinding;
import com.xiongch.mvvm.ui.index.viewmodel.MessageViewModel;

import javax.inject.Inject;

public class MessageActivity extends BaseActivity<ActivityMessageBinding, MessageViewModel> {

    @Inject
    MessageViewModel messageViewModel;

    public MessageActivity() {
        super(R.layout.activity_message);
    }

    @Override
    public void initImmersionBar() {
        super.initImmersionBar();
        mImmersionBar = ImmersionBar.with(this).statusBarDarkFont(false,0.2f).keyboardEnable(true);
        mImmersionBar.init();
    }

    @Override
    protected View getStatusBarView() {
        return mBinding.titleBar.rlTitlebar;
    }

    @Override
    protected void bindDataBindingAndViewModel() {
        mViewModel = (MessageViewModel) getViewModel(messageViewModel);
        mBinding.setViewModel(mViewModel);
        mBinding.setActivity(this);
    }

    @Override
    protected void initView() {

    }

    public void onIvLeftClick(){
        finish();
    }
}
