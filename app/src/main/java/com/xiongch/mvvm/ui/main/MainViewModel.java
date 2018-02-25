package com.xiongch.mvvm.ui.main;

import com.xclib.mvvm.BaseViewModel;

import javax.inject.Inject;

/**
 * Created by xiongch on 2018/1/4.
 */

public class MainViewModel extends BaseViewModel {

    MainModel mainModel;

    public MainViewModel(MainModel mainModel) {
        this.mainModel = mainModel;
    }



}
