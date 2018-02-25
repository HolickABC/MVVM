package com.xclib.loadsir;

import android.content.Context;
import android.view.View;

import com.kingja.loadsir.callback.Callback;
import com.xclib.R;


public class EmptyCallback extends Callback {

    @Override
    protected int onCreateView() {
        return R.layout.inflate_empty_view;
    }

    @Override
    protected boolean onRetry(Context context, View view) {
        return true;
    }
}
