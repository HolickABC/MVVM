package com.xiongch.mvvm.di.module.fragment;

import com.xclib.di.scope.FragmentScope;
import com.xiongch.mvvm.ui.index.model.IndexModel;
import com.xiongch.mvvm.ui.index.viewmodel.IndexViewModel;

import dagger.Module;
import dagger.Provides;

/**
 * Created by xiongch on 2018/1/5.
 */

@Module
public class IndexFragmentModule {

    @FragmentScope
    @Provides
    IndexModel provideIndexModel(){
        return new IndexModel();
    }

    @FragmentScope
    @Provides
    IndexViewModel provideIndexViewModel(IndexModel indexModel){
        return new IndexViewModel(indexModel);
    }


}
