package com.xclib.adapter;

import android.databinding.ViewDataBinding;
import android.view.View;

import com.chad.library.adapter.base.BaseViewHolder;
import com.xclib.R;


/**
 * Created by xiongchang on 2017/11/1.
 *
 * 用于DataBinding
 */

public class MyBaseViewHolder extends BaseViewHolder {

    public MyBaseViewHolder(View view) {
        super(view);
    }

    public ViewDataBinding getBinding() {
        return (ViewDataBinding) itemView.getTag(R.id.BaseQuickAdapter_databinding_support);
    }
}
