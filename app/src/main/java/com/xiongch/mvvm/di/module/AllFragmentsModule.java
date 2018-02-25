package com.xiongch.mvvm.di.module;

import com.xclib.base.BaseFragmentComponent;
import com.xclib.di.scope.FragmentScope;
import com.xiongch.mvvm.ui.index.IndexFragment;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

/**
 * Created by xiongch on 2018/1/4.
 */

@Module(subcomponents = {
        BaseFragmentComponent.class
})
public abstract class AllFragmentsModule {

    @FragmentScope
    @ContributesAndroidInjector(modules = IndexFragmentModule.class)
    abstract IndexFragment contributeIndexFragmentInjector();

}
