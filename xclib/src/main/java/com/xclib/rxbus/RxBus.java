package com.xclib.rxbus;


import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subjects.PublishSubject;
import io.reactivex.subjects.Subject;

/**
 * Created by xiongchang on 2017/10/28.
 */

public class RxBus {

    private static volatile RxBus mInstance;
    private final Subject<Object> subject = PublishSubject.create().toSerialized();
    private Disposable disposable;

    private RxBus(){

    }

    public static RxBus getInstance(){
        if(mInstance == null){
            synchronized (RxBus.class){
                if(mInstance == null){
                    mInstance = new RxBus();
                }
            }
        }
        return mInstance;
    }

    /**
     * 发送事件
     */
    public void post(Object object){
        subject.onNext(object);
    }

    /**
     * @param classType
     * @param <T>
     * @return
     */
    public <T> Observable<T> toObservable(Class<T> classType) {
        return subject.ofType(classType);
    }

    /**
     * 订阅
     * @param bean
     * @param consumer
     */
    public Disposable subscribe(Class bean, Consumer<RxBusEvent> consumer) {
        return disposable = toObservable(bean)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(consumer);
    }


    /**
     * 取消订阅
     * RxBus.getInstance().unSubscribe();
     */
    public void unSubscribe(){
        if (disposable != null && disposable.isDisposed()){
            disposable.dispose();
        }

    }

}
