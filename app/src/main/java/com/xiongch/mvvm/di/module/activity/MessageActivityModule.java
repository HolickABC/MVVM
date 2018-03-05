package com.xiongch.mvvm.di.module.activity;

import android.support.v4.app.Fragment;

import com.xclib.di.scope.ActivityScope;
import com.xiongch.mvvm.ui.index.IndexFragment;
import com.xiongch.mvvm.ui.index.model.MessageModel;
import com.xiongch.mvvm.ui.index.viewmodel.MessageViewModel;
import com.xiongch.mvvm.ui.loan.LoanFragment;
import com.xiongch.mvvm.ui.me.MeFragment;

import java.util.ArrayList;
import java.util.List;

import dagger.Module;
import dagger.Provides;

/**
 * Created by xiongch on 2018/1/4.
 */

@Module
public class MessageActivityModule {

    @ActivityScope
    @Provides
    MessageModel provideMessageModel(){
        return new MessageModel();
    }

    @ActivityScope
    @Provides
    MessageViewModel provideMessageViewModel(MessageModel messageModel){
        return new MessageViewModel(messageModel);
    }

}
