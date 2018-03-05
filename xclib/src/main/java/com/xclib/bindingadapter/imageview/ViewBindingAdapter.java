package com.xclib.bindingadapter.imageview;

import android.databinding.BindingAdapter;
import android.text.TextUtils;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.xclib.R;

import jp.wasabeef.glide.transformations.CropCircleTransformation;


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
        if(TextUtils.isEmpty(url)){
            imageView.setImageResource(R.drawable.ic_img_default);
        }else {
            Glide.with(imageView.getContext()).load(url)
                    .centerCrop()
                    .placeholder(R.drawable.ic_img_default).error(R.drawable.ic_img_default)
                    .crossFade().into(imageView);
        }
    }

    /**
     * 加载网络图
     * @param imageView
     * @param url
     */
    @BindingAdapter("avatarUrl")
    public static void avatarUrl(ImageView imageView, String url){
        if(TextUtils.isEmpty(url)){
            imageView.setImageResource(R.drawable.ic_head_default);
        }else {
            Glide.with(imageView.getContext()).load(url)
                    .bitmapTransform(new CropCircleTransformation(imageView.getContext()))
                    .placeholder(R.drawable.ic_head_default).error(R.drawable.ic_head_default)
                    .crossFade()
                    .into(new SimpleTarget<GlideDrawable>() {
                        @Override
                        public void onResourceReady(GlideDrawable resource, GlideAnimation<? super GlideDrawable> glideAnimation) {
                            imageView.setImageDrawable(resource);
                        }
                    });
        }
    }

    /**
     * 加载资源文件
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
