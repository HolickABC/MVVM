package com.xiongch.mvvm.ui.main;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;

import com.xclib.adapter.FragmentVpAdapter;
import com.xclib.base.BaseActivity;
import com.xclib.widget.MenuBar;
import com.xiongch.mvvm.R;
import com.xiongch.mvvm.databinding.ActivityMainBinding;
import com.xiongch.mvvm.ui.index.IndexFragment;
import com.xiongch.mvvm.ui.loan.LoanFragment;
import com.xiongch.mvvm.ui.me.MeFragment;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class  MainActivity extends BaseActivity<ActivityMainBinding, MainViewModel> implements MenuBar.OnMenuSelectedListener {

    @Inject
    MainViewModel mainViewModel;
    MenuBar menuBar;
    @Inject
    List<Fragment> fragmentList;

    public MainActivity() {
        super(R.layout.activity_main, true);
    }

    @Override
    protected void bindDataBindingAndViewModel() {
        mViewModel = (MainViewModel) getViewModel(MainViewModel.class, mainViewModel);
        mBinding.setViewModel(mViewModel);
    }

    @Override
    protected void initView() {
        initMenuBar();
        initViewPager();
    }

    private void initViewPager() {
        mBinding.viewPager.setNoScroll(true);
        mBinding.viewPager.setOffscreenPageLimit(3);
        mBinding.viewPager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return fragmentList.get(position);
            }

            @Override
            public int getCount() {
                return fragmentList.size();
            }
        });
    }

    private void initMenuBar() {
        menuBar = new MenuBar(this,this);
    }

    @Override
    public void onMenuSelected(int position) {
        mBinding.viewPager.setCurrentItem(position);
    }
}
