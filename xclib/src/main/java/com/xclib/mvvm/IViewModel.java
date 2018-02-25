package com.xclib.mvvm;

/**
 * Created by xiongch on 2018/1/4.
 */

public interface IViewModel {

    void start();

    void showNetworkErrorView();

    void showEmptyView();

    void showSuccessfulView();

    void showErrorView();
}
