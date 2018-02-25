package com.xiongch.mvvm.di.module;

import com.xclib.di.scope.FragmentScope;
import com.xiongch.mvvm.ui.index.IndexModel;
import com.xiongch.mvvm.ui.index.IndexViewModel;

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
