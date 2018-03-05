package com.xiongch.mvvm.ui.index;


import android.arch.lifecycle.Observer;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.kingja.loadsir.callback.Callback;
import com.kingja.loadsir.core.LoadSir;
import com.xclib.base.LazyFragment;
import com.xclib.util.DialogUtil;
import com.xiongch.mvvm.R;
import com.xiongch.mvvm.databinding.FragmentIndexBinding;
import com.xiongch.mvvm.ui.index.adapter.IndexAdapter;
import com.xiongch.mvvm.ui.index.viewmodel.IndexViewModel;

import javax.inject.Inject;


public class IndexFragment extends LazyFragment<FragmentIndexBinding, IndexViewModel> implements BaseQuickAdapter.OnItemChildClickListener {

    @Inject
    IndexViewModel indexViewModel;
    IndexAdapter adapter;

    public IndexFragment() {
        super(R.layout.fragment_index);
    }

    @Override
    public void loadData() {
        mViewModel.onRefresh();
    }



    @Override
    protected View getStatusBarView() {
        return null;
    }

    @Override
    protected void bindDataBindingAndViewModel() {
        mViewModel = (IndexViewModel) getViewModel(indexViewModel);
        mBinding.setViewModel(mViewModel);
    }

    @Override
    protected void initView() {
        initRecyclerView();
    }

    private void initRecyclerView() {
        adapter = new IndexAdapter();
        mBinding.recyclerView.setAdapter(adapter);

        adapter.setOnItemChildClickListener(this);
    }

    @Override
    public void initLoadSir(View mRootView) {
        super.initLoadSir(mRootView);
        mLoadService = LoadSir.getDefault().register(mRootView, new Callback.OnReloadListener() {
            @Override
            public void onReload(View v) {
                mViewModel.onRefresh();
            }
        });
        //绑定到dataBinding中
        mBinding.setLoadService(mLoadService);
    }

    @Override
    public View getFragmentReturnLayout(View mRootView) {
        return mLoadService.getLoadLayout();
    }

    @Override
    public void initObservable() {
        super.initObservable();
        mViewModel.showDialog.observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(@Nullable Boolean aBoolean) {
                DialogUtil.getAlertDialog(getContext(), "Dialog显示在View层", "确定").show();
            }
        });
    }

    @Override
    public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
        switch (view.getId()){
            case R.id.cardView:
                startActivity(new Intent(getContext(), MessageActivity.class));
                break;
        }
    }
}
