package com.xclib.adapter;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.view.View;
import android.view.ViewGroup;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.xclib.R;

import java.util.List;


/**
 * Created by xiongchang on 2017/11/1.
 *
 * 用于DataBinding
 */

public abstract class MyBaseMultiAdapter<T extends MultiItemEntity, K extends BaseViewHolder> extends BaseMultiItemQuickAdapter<T,K> {

    public MyBaseMultiAdapter(List<T> data) {
        super(data);
        openLoadAnimation(BaseQuickAdapter.ALPHAIN);
    }

    @Override
    protected View getItemView(int layoutResId, ViewGroup parent) {
        ViewDataBinding binding = DataBindingUtil.inflate(mLayoutInflater, layoutResId, parent, false);
        if (binding == null) {
            return super.getItemView(layoutResId, parent);
        }
        View view = binding.getRoot();
        view.setTag(R.id.BaseQuickAdapter_databinding_support, binding);
        return view;
    }

    @Override
    protected void convert(K helper, T item) {
        //自己重写
        bind(helper,item);
        //防止闪烁  立即更新
        ((ViewDataBinding)helper.itemView.getTag(R.id.BaseQuickAdapter_databinding_support)).executePendingBindings();
    }

    protected abstract void bind(K helper, T item);
}
