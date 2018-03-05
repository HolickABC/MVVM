package com.xiongch.mvvm.di.module;

import com.xclib.base.BaseActivityComponent;
import com.xclib.di.scope.ActivityScope;
import com.xiongch.mvvm.di.module.activity.MainActivityModule;
import com.xiongch.mvvm.di.module.activity.MessageActivityModule;
import com.xiongch.mvvm.ui.index.MessageActivity;
import com.xiongch.mvvm.ui.main.MainActivity;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

/**
 * Created by xiongch on 2018/1/4.
 */

@Module(subcomponents = {
        BaseActivityComponent.class
})
public abstract class AllActivitiesModule {

    @ActivityScope
    @ContributesAndroidInjector(modules = MainActivityModule.class)
    abstract MainActivity contributeMainActivityInjector();

    @ActivityScope
    @ContributesAndroidInjector(modules = MessageActivityModule.class)
    abstract MessageActivity contributeMessageActivityInjector();
}
