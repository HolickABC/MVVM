package com.xiongch.mvvm.di.module;

import android.support.v4.app.Fragment;

import com.xclib.di.scope.ActivityScope;
import com.xiongch.mvvm.ui.index.IndexFragment;
import com.xiongch.mvvm.ui.loan.LoanFragment;
import com.xiongch.mvvm.ui.main.MainModel;
import com.xiongch.mvvm.ui.main.MainViewModel;
import com.xiongch.mvvm.ui.me.MeFragment;

import java.util.ArrayList;
import java.util.List;

import dagger.Module;
import dagger.Provides;

/**
 * Created by xiongch on 2018/1/4.
 */

@Module
public class MainActivityModule {

    @ActivityScope
    @Provides
    MainModel provideMainModel(){
        return new MainModel();
    }

    @ActivityScope
    @Provides
    MainViewModel provideMainViewModel(MainModel mainModel){
        return new MainViewModel(mainModel);
    }

    @ActivityScope
    @Provides
    IndexFragment provideIndexFragment(){
        return new IndexFragment();
    }

    @ActivityScope
    @Provides
    LoanFragment provideLoanFragment(){
        return new LoanFragment();
    }

    @ActivityScope
    @Provides
    MeFragment provideMeFragment(){
        return new MeFragment();
    }

    @ActivityScope
    @Provides
    List<Fragment> provideFragmentList(IndexFragment indexFragment, LoanFragment loanFragment, MeFragment meFragment){
        List<Fragment> fragmentList = new ArrayList<>();
        fragmentList.add(indexFragment);
        fragmentList.add(loanFragment);
        fragmentList.add(meFragment);
        return fragmentList;
    }

}
