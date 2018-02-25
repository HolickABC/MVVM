package com.xiongch.mvvm.ui.index;

import android.databinding.ObservableArrayList;
import android.databinding.ObservableBoolean;
import android.databinding.ObservableList;

import com.xclib.http.DefaultDisposableObserver;
import com.xclib.mvvm.BaseViewModel;
import com.xiongch.mvvm.ui.index.bean.IndexItem;

import java.util.List;

import io.reactivex.disposables.Disposable;

/**
 * Created by xiongch on 2018/1/5.
 */

public class IndexViewModel extends BaseViewModel {

    IndexModel indexModel;
    public ObservableBoolean isRefresh = new ObservableBoolean();
    public ObservableBoolean isLoadMore = new ObservableBoolean();
    public ObservableArrayList<IndexItem> items = new ObservableArrayList<>();


    public IndexViewModel(IndexModel indexModel) {
        this.indexModel = indexModel;
    }

    public void refreshRecyclerView(){
        isRefresh.set(true);
        Disposable disposable = indexModel.refreshRecyclerView(new DefaultDisposableObserver<List<IndexItem>>(this) {
            @Override
            protected void _onNext(List<IndexItem> indexItems) {
                isLoadMore.set(false);
                items.clear();
                items.addAll(indexItems);
            }

            @Override
            protected void _onError(Throwable e) {
                isRefresh.set(false);
            }

            @Override
            protected void _onCompleted() {
                isRefresh.set(false);
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
