package com.xclib.http;


import com.xclib.util.MyLogUtil;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.exceptions.Exceptions;
import io.reactivex.functions.Function;

/**
 * Created by xiongchang on 17/6/27.
 */

public class RxHelper {

    //对结果进行预处理
    public static <T> ObservableTransformer<BaseModel<T>, T> handleResult(){
        return new ObservableTransformer<BaseModel<T>, T>() {

            @Override
            public ObservableSource<T> apply(Observable<BaseModel<T>> baseModelObservable) {
                return baseModelObservable.flatMap(new Function<BaseModel<T>, Observable<T>>() {
                    @Override
                    public Observable<T> apply(BaseModel<T> result) throws Exception {
                        MyLogUtil.i("test", "rxhelper --- result from network: " + result);
                        try {
                            if(result != null){
                                if(result.success()){
                                    return createData(result.data);
                                }else {
//                                    String message = ErrorMessage.get(result.code);
                                    String message = result.msg;
                                    return Observable.error(new APIException(result.code,message));
                                }
                            }else {
                                return null;
                            }
                        }catch (Exception ex) {
                            if (ex instanceof RuntimeException) {
                                throw ex;
                            } else {
                                throw Exceptions.propagate(ex);
                            }
                        }
                    }
                }).compose(new SchedulerTransformer<T>())
                        .retryWhen(new RetryWhenNetworkException(2, 1000));
            }
        };
    }

    //创建成功的数据
    private static <T> Observable<T> createData(final T data) {
        return Observable.create(new ObservableOnSubscribe<T>() {
            @Override
            public void subscribe(ObservableEmitter<T> emitter) throws Exception {
                try {
                    if(data == null){
                        emitter.onNext((T) new Object());
                    }else {
                        emitter.onNext(data);
                    }
                    emitter.onComplete();
                }catch (Exception e){
                    emitter.onError(e);
                }
            }
        });
    }
}
