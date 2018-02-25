package com.xclib.base;

import android.arch.lifecycle.ViewModelProvider;

import com.xclib.base.BaseActivity;

import dagger.Binds;
import dagger.Subcomponent;
import dagger.android.AndroidInjectionModule;
import dagger.android.AndroidInjector;

/**
 * Created by xiongch on 2018/1/4.
 */

@Subcomponent(modules = {
        AndroidInjectionModule.class
})
public interface BaseActivityComponent extends AndroidInjector<BaseActivity> {

    //每一个继承BaseActivity的Activity，都共享同一个SubComponent
    @Subcomponent.Builder
    abstract class Builder extends AndroidInjector.Builder<BaseActivity> {

    }

}
