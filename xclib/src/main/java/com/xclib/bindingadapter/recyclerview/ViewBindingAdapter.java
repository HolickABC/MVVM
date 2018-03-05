package com.xclib.bindingadapter.recyclerview;

import android.databinding.BindingAdapter;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.blankj.utilcode.util.ToastUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.xclib.toast.ToastHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xiongchang on 2017/10/30.
 *
 * 实现基于BaseRecyclerViewAdapterHelper
 */

public class ViewBindingAdapter {

    /**
     * 每次传入的data  都是每次请求的集合  并不是adapter的原始集合
     *
     * @param recyclerView
     * @param data
     * @param isLoadMore
     */
    @BindingAdapter(value = {"data", "isLoadMore"})
    public static void setData(RecyclerView recyclerView, List<?> data, boolean isLoadMore){
        int PAGE_SIZE = 10;

        BaseQuickAdapter adapter = ((BaseQuickAdapter)recyclerView.getAdapter());
        if(adapter ==null || data == null){
            return;
        }

        if(isLoadMore == false){
            adapter.replaceData(data);
            adapter.loadMoreComplete();
            //不足一页不显示加载更多
            if(adapter.getData().size() < PAGE_SIZE){
                adapter.loadMoreEnd(true);
            }
        }else {
            if (data.size() < PAGE_SIZE) {
                //如果不够一页则显示加载完成
                adapter.loadMoreEnd();
//                ToastHelper.showShort("没有更多数据了");
            } else {
                adapter.loadMoreComplete();
            }

            //TODO  未解决问题 如果调用setNewData()  为什么这个data已经同步到adapter中了
            //TODO 已解决  源码中  replaceData()不会改变adapter中数据源的引用  而setNewData()会改变引用  shit
            //TODO replaceData()  -->  addData()
            //TODO setNewData() --> 直接notifyDataSetChanged() 因为已经改变为参数中data的引用了  所以会循环导致DataBinding的改变  原因data.add(data)
            adapter.addData(data);



        /**
         * 以下为错误方法
         *
         * (data.size() - adapter.getData().size()
         * 这行代码 由于数据源的引用已经改变  adapter中的数据源就是data 无法拿到之前的adapter中的数据源 所以相减一直为0
         */


//        if(isLoadMore == false){
//            adapter.setNewData(data);
//            //不足一页不显示加载更多
//            if(adapter.getData().size() < PAGE_SIZE){
//                adapter.loadMoreEnd(true);
//            }
//        }else {
//            if ((data.size() - adapter.getData().size()) < PAGE_SIZE) {
//                //如果不够一页则显示加载完成
//                adapter.loadMoreEnd();
//                ToastUtils.showShort("没有更多数据了");
//            } else {
//                adapter.loadMoreComplete();
//            }
//
//            //TODO  未解决问题 如果调用setNewData()  为什么这个data已经同步到adapter中了
//            //TODO 已解决  源码中  replaceData()不会改变adapter中数据源的引用  而setNewData()会改变引用  shit
//            //TODO replaceData()  -->  addData()
//            //TODO setNewData() --> 直接notifyDataSetChanged() 因为已经改变为参数中data的引用了  所以会循环导致DataBinding的改变  原因data.add(data)
//
//            //解决方法
//            adapter.notifyDataSetChanged();
        }

    }

    @BindingAdapter("loadMore")
    public static void loadMore(RecyclerView recyclerView, BaseQuickAdapter.RequestLoadMoreListener listener){
        BaseQuickAdapter adapter = ((BaseQuickAdapter)recyclerView.getAdapter());
        if(adapter != null){
//            adapter.setOnLoadMoreListener(listener);
            adapter.setOnLoadMoreListener(listener, recyclerView);
        }
    }


}
