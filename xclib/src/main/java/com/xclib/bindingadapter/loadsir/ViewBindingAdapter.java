package com.xclib.bindingadapter.loadsir;

import android.databinding.BindingAdapter;
import android.view.View;
import android.view.ViewGroup;

import com.kingja.loadsir.callback.SuccessCallback;
import com.kingja.loadsir.core.LoadService;
import com.xclib.loadsir.EmptyCallback;
import com.xclib.loadsir.ErrorCallback;
import com.xclib.loadsir.NetworkErrorCallback;
import com.xclib.loadsir.Status;

/**
 * Created by xiongch on 2018/1/6.
 */

public class ViewBindingAdapter {

    /**
     * 各种情况的状态图
     * @param view
     * @param loadService
     * @param status
     */
    @BindingAdapter(value = {"loadService", "status"})
    public static void showLoadSirStatus(View view, LoadService loadService, Status status){
        switch (status){
            case NETWORK_ERROR:
                loadService.showCallback(NetworkErrorCallback.class);
                break;

            case EMPTY:
                loadService.showCallback(EmptyCallback.class);
                break;

            case SUCCESS:
                loadService.showCallback(SuccessCallback.class);
                break;

            case ERROR:
                loadService.showCallback(ErrorCallback.class);
                break;
        }
    }
}
