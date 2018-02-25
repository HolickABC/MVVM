package com.xiongch.mvvm.ui.index;


import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.kingja.loadsir.callback.Callback;
import com.kingja.loadsir.core.LoadSir;
import com.xclib.base.LazyFragment;
import com.xiongch.mvvm.R;
import com.xiongch.mvvm.databinding.FragmentIndexBinding;
import com.xiongch.mvvm.ui.index.adapter.IndexAdapter;

import javax.inject.Inject;


public class IndexFragment extends LazyFragment<FragmentIndexBinding, IndexViewModel> implements SwipeRefreshLayout.OnRefreshListener,BaseQuickAdapter.RequestLoadMoreListener {

    @Inject
    IndexViewModel indexViewModel;
    IndexAdapter adapter;

    public IndexFragment() {
        super(R.layout.fragment_index);
    }

    @Override
    public void loadData() {
        onRefresh();
    }

    @Override
    protected void bindDataBindingAndViewModel() {
        mViewModel = (IndexViewModel) getViewModel(IndexViewModel.class, indexViewModel);
        mBinding.setViewModel(mViewModel);
    }

    @Override
    protected void initView() {
        initRefreshLayout();
        initRecyclerView();
    }

    private void initRecyclerView() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        mBinding.recyclerView.setLayoutManager(linearLayoutManager);
        adapter = new IndexAdapter();
        mBinding.recyclerView.setAdapter(adapter);
        //上拉加载
        adapter.setOnLoadMoreListener(this, mBinding.recyclerView);
    }

    private void initRefreshLayout() {
        mBinding.refreshLayout.setColorSchemeResources(R.color.colorPrimary);
        mBinding.refreshLayout.setOnRefreshListener(this);
    }


    @Override
    public void onRefresh() {
        mViewModel.refreshRecyclerView();
    }

    @Override
    public void onLoadMoreRequested() {
        mViewModel.loadMore();
    }

    @Override
    public void initLoadSir(View mRootView) {
        super.initLoadSir(mRootView);
        mLoadService = LoadSir.getDefault().register(mRootView, new Callback.OnReloadListener() {
            @Override
            public void onReload(View v) {
                onRefresh();
            }
        });
        //绑定到dataBinding中
        mBinding.setLoadService(mLoadService);
    }

    @Override
    public View getFragmentReturnLayout(View mRootView) {
        return mLoadService.getLoadLayout();
    }
}
