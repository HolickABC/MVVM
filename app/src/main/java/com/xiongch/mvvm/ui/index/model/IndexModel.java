package com.xiongch.mvvm.ui.index.model;

import com.xclib.http.DefaultDisposableObserver;
import com.xclib.http.SchedulerTransformer;
import com.xiongch.mvvm.R;
import com.xiongch.mvvm.ui.index.bean.IndexItem;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.BiFunction;

/**
 * Created by xiongch on 2018/1/5.
 */

public class IndexModel {

    public Disposable refreshRecyclerView(DefaultDisposableObserver<List<IndexItem>> observer){
        List<IndexItem> datas = new ArrayList<>();

        return Observable.zip(
                requestCarousel(),
                requestText(),
                new BiFunction<IndexItem, List<IndexItem>, List<IndexItem>>() {
                    @Override
                    public List<IndexItem> apply(IndexItem localItem, List<IndexItem> localItems) throws Exception {
                        datas.add(localItem);

                        for(IndexItem item : localItems){
                            datas.add(item);
                        }

                        return datas;
                    }
                }
        ).compose(new SchedulerTransformer<>()).delay(2000, TimeUnit.MILLISECONDS).subscribeWith(observer);
    }

    public Observable<IndexItem> requestCarousel(){
        return Observable.create(new ObservableOnSubscribe<IndexItem>() {
            @Override
            public void subscribe(ObservableEmitter<IndexItem> e) throws Exception {
                List<Integer> data = new ArrayList<>();
                for(int i=0; i<5; i++){
                    data.add(R.drawable.img_test);
                }
                IndexItem item = new IndexItem(data,IndexItem.CAROUSEL);
                e.onNext(item);
                e.onComplete();
            }
        });
    }

    public Observable<List<IndexItem>> requestText() {
        return Observable.create(new ObservableOnSubscribe<List<IndexItem>>() {
            @Override
            public void subscribe(ObservableEmitter<List<IndexItem>> e) throws Exception {
                List<IndexItem> data = new ArrayList<>();
                for(int i=0; i<10; i++){
                    IndexItem item = new IndexItem(i+"",IndexItem.TEXT);
                    data.add(item);
                }

                e.onNext(data);
                e.onComplete();
            }
        });
    }

    public Disposable loadMore(DefaultDisposableObserver<List<IndexItem>> observer){
        return Observable.create(new ObservableOnSubscribe<List<IndexItem>>() {
            @Override
            public void subscribe(ObservableEmitter<List<IndexItem>> e) throws Exception {
                List<IndexItem> datas = new ArrayList<>();
                for(int i=0; i<5; i++){
                    IndexItem item = new IndexItem(i+" new",IndexItem.TEXT);
                    datas.add(item);
                }

                e.onNext(datas);
                e.onComplete();
            }
        }).compose(new SchedulerTransformer<List<IndexItem>>()).delay(2000, TimeUnit.MILLISECONDS).subscribeWith(observer);
    }
}
