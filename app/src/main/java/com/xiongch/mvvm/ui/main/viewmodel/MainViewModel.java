package com.xiongch.mvvm.ui.main.viewmodel;

import com.xclib.mvvm.BaseViewModel;
import com.xiongch.mvvm.ui.main.model.MainModel;

/**
 * Created by xiongch on 2018/1/4.
 */

public class MainViewModel extends BaseViewModel {

    MainModel mainModel;

    public MainViewModel(MainModel mainModel) {
        this.mainModel = mainModel;
    }



}
