package com.xclib.loadsir;


import com.kingja.loadsir.callback.Callback;
import com.xclib.R;


public class ErrorCallback extends Callback {
    @Override
    protected int onCreateView() {
        return R.layout.inflate_error_view;
    }
}
