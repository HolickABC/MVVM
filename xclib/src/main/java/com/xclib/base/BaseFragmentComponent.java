package com.xclib.base;

import dagger.Subcomponent;
import dagger.android.AndroidInjectionModule;
import dagger.android.AndroidInjector;
import dagger.android.support.AndroidSupportInjectionModule;

/**
 * Created by xiongch on 2018/1/4.
 */

@Subcomponent(modules = {
        AndroidSupportInjectionModule.class
})
public interface BaseFragmentComponent extends AndroidInjector<BaseFragment> {

    //每一个继承BaseFragment的Fragment，都共享同一个SubComponent
    @Subcomponent.Builder
    abstract class Builder extends AndroidInjector.Builder<BaseFragment> {

    }

}
