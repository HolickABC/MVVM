package com.xiongch.mvvm.di.component;

import com.xiongch.mvvm.application.MyApplication;
import com.xiongch.mvvm.di.module.AllActivitiesModule;
import com.xiongch.mvvm.di.module.AllFragmentsModule;

import dagger.Component;
import dagger.android.AndroidInjectionModule;
import dagger.android.support.AndroidSupportInjectionModule;

/**
 * Created by xiongch on 2018/1/4.
 */

@Component(modules = {
        AndroidInjectionModule.class,
        AndroidSupportInjectionModule.class,
        AllActivitiesModule.class,
        AllFragmentsModule.class
})
public interface MyApplicationComponent {

    void inject(MyApplication application);

}
