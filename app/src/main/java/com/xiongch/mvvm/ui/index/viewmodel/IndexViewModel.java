package com.xiongch.mvvm.ui.index.viewmodel;

import android.arch.lifecycle.MutableLiveData;
import android.databinding.ObservableArrayList;
import android.databinding.ObservableBoolean;

import com.xclib.http.DefaultDisposableObserver;
import com.xclib.mvvm.BaseViewModel;
import com.xiongch.mvvm.ui.index.bean.IndexItem;
import com.xiongch.mvvm.ui.index.model.IndexModel;

import java.util.List;

import io.reactivex.disposables.Disposable;

/**
 * Created by xiongch on 2018/1/5.
 */

public class IndexViewModel extends BaseViewModel {

    private IndexModel indexModel;

    public MutableLiveData<Boolean> showDialog = new MutableLiveData<>();

    public ObservableBoolean isRefresh = new ObservableBoolean();
    public ObservableBoolean isLoadMore = new ObservableBoolean();
    public ObservableArrayList<IndexItem> items = new ObservableArrayList<>();

    public IndexViewModel(IndexModel indexModel) {
        this.indexModel = indexModel;
    }

    public void onRefresh(){
        isRefresh.set(true);
        Disposable disposable = indexModel.refreshRecyclerView(new DefaultDisposableObserver<List<IndexItem>>(this) {
            @Override
            protected void _onNext(List<IndexItem> indexItems) {
                isLoadMore.set(false);
                items.clear();
                items.addAll(indexItems);

                showDialog.postValue(true);
            }

            @Override
            protected void _onError(Throwable e) {
                isRefresh.set(false);
                showDialog.postValue(false);
            }

            @Override
            protected void _onCompleted() {
                isRefresh.set(false);
                showDialog.postValue(false);
            }
        });
        mCompositeDisposable.add(disposable);
    }

    public void loadMore() {
        Disposable disposable = indexModel.loadMore(new DefaultDisposableObserver<List<IndexItem>>() {
            @Override
            protected void _onNext(List<IndexItem> indexItems) {
                isLoadMore.set(true);
                items.clear();
                items.addAll(indexItems);
            }

            @Override
            protected void _onError(Throwable e) {
            }

            @Override
            protected void _onCompleted() {
            }
        });
        mCompositeDisposable.add(disposable);
    }
}
