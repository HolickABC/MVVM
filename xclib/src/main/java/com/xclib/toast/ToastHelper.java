package com.xclib.toast;

import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import com.blankj.utilcode.util.ToastUtils;
import com.xclib.R;

/**
 * Created by xiongch on 2018/1/8.
 *
 * 用于扩展toast
 */

public class ToastHelper {

    public static void showShort(String message){


//        ToastUtils.showShort(message);

        View view = ToastUtils.showCustomShort(R.layout.inflate_toast_layout);
        TextView textView = view.findViewById(R.id.inflate_toast_layout_tv);
        textView.setText(message);
        ToastUtils.setGravity(Gravity.CENTER,0,0);
    }
}
