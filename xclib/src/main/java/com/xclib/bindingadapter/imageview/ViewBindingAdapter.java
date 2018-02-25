package com.xclib.bindingadapter.imageview;

import android.databinding.BindingAdapter;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.xclib.R;


/**
 * Created by xiongchang on 2017/10/30.
 */

public class ViewBindingAdapter {

    /**
     * 加载网络图
     * @param imageView
     * @param url
     */
    @BindingAdapter("imageUrl")
    public static void imageUrl(ImageView imageView, String url){
        if(url==null){
            imageView.setImageResource(R.drawable.image_mark);
        }else {
            Glide.with(imageView.getContext()).load(url)
                    .placeholder(R.drawable.image_mark).error(R.drawable.image_mark)
                    .crossFade().into(imageView);
        }
    }

    /**
     * 加载大图
     * @param imageView
     * @param resId
     */
    @BindingAdapter("imageRes")
    public static void imageRes(ImageView imageView, int resId){
        Glide.with(imageView.getContext()).load(resId)
                .placeholder(R.drawable.image_mark).error(R.drawable.image_mark)
                .crossFade().into(imageView);
    }
}
