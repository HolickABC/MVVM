package com.xclib.http;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.Window;
import android.widget.ProgressBar;

import com.gyf.barlibrary.ImmersionBar;
import com.xclib.R;
import com.xclib.base.BaseApplication;

/**
 * Created by xiongch on 2018/1/22.
 *
 * 用于显示ProgressDialog
 */

public class ProgressDialogActivity extends Activity {
    public static Activity mActivity;
    private boolean isShow;
    private ProgressDialog progressDialog;

    public static void start(Context context, boolean isShow){
        Intent intent = new Intent(context, ProgressDialogActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra("isShow", isShow);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        mActivity = this;

        //获取intent参数
        isShow = getIntent().getBooleanExtra("isShow",false);


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_progress_dialog);

        if(isShow){
//            progressDialog = new ProgressDialog(this);
//            progressDialog.setIndeterminate(false);
//            progressDialog.setMessage("加载中...");
//            progressDialog.setCancelable(false);
//            progressDialog.show();
        }
    }

    @Override
    protected void onDestroy() {
        if(progressDialog != null){
            progressDialog.dismiss();
            progressDialog = null;
        }
        super.onDestroy();
    }

}
