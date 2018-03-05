package com.xclib.mvvm;

import android.content.Context;

/**
 * Created by xiongch on 2018/1/4.
 */

public interface IViewModel {

    void onStart();

    void showNetworkErrorView();

    void showEmptyView();

    void showSuccessfulView();

    void showErrorView();

}
