package com.xiongch.mvvm.ui.index.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;

import com.blankj.utilcode.util.ToastUtils;
import com.xiongch.mvvm.databinding.IndexFragmentBannerItemBinding;
import com.zhouwei.mzbanner.holder.MZViewHolder;

/**
 * Created by xiongch on 2018/1/5.
 */

public class BannerViewHolder implements MZViewHolder<Integer> {

    IndexFragmentBannerItemBinding binding;

    @Override
    public View createView(Context context) {
        binding = IndexFragmentBannerItemBinding.inflate(LayoutInflater.from(context));
        return binding.getRoot();
    }

    @Override
    public void onBind(Context context, int i, Integer integer) {
        binding.setData(integer);
        binding.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToastUtils.showShort(i+"");
            }
        });
        binding.executePendingBindings();
    }
}
