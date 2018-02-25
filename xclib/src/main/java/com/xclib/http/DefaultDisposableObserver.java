package com.xclib.http;

import android.app.Dialog;
import android.content.Context;
import com.blankj.utilcode.util.ToastUtils;
import com.xclib.base.BaseApplication;
import com.xclib.mvvm.IViewModel;
import com.xclib.rxbus.RxBus;
import com.xclib.rxbus.RxBusEvent;
import com.xclib.util.DialogUtil;
import com.xclib.util.MyLogUtil;
import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.net.UnknownServiceException;
import java.util.List;
import io.reactivex.observers.DisposableObserver;
import retrofit2.HttpException;

/**
 * Created by xiongchang on 2017/10/28.
 */

public abstract class DefaultDisposableObserver<T> extends DisposableObserver<T> {
    private Context context;
    private boolean showProgressDialog = false;
    private Dialog dialog;
    IViewModel viewModel;

    public DefaultDisposableObserver(){

    }

    public DefaultDisposableObserver(boolean showProgressDialog){
        this.context = BaseApplication.getInstance().getApplicationContext();
        this.showProgressDialog = showProgressDialog;
        dialog = DialogUtil.getProgressDialog(context,"加载中...");
    }

    //此构造方法用于显示状态图
    public DefaultDisposableObserver(IViewModel iViewModel){
        this.viewModel = iViewModel;
    }

    @Override
    protected void onStart() {
        super.onStart();
        showProgressDialog();
    }

    @Override
    public void onComplete() {
        hideProgressDialog();
        _onCompleted();
    }

    @Override
    public void onNext(T t) {
        if(viewModel!=null && t instanceof List<?> && (t==null || ((List) t).size()==0)){//列表--recyclerview
            viewModel.showEmptyView();
        }else if(viewModel!=null && t instanceof List<?>){//不为空
            viewModel.showSuccessfulView();
            _onNext(t);
        }else {
            _onNext(t);
        }
    }

    @Override
    public void onError(Throwable e) {
        hideProgressDialog();
        if (e instanceof SocketTimeoutException || e instanceof ConnectException || e instanceof UnknownHostException || e instanceof UnknownServiceException || e instanceof HttpException) {
            ToastUtils.showShort("网络异常");

            //注册viewModel    显示网络异常的view
            if (viewModel != null) {
                viewModel.showNetworkErrorView();
            }
        } else if(e instanceof APIException){
            if(viewModel!=null){
                viewModel.showSuccessfulView();
            }

            //服务器状态错误
            int code = ((APIException)e).getCode();
            String message = e.getMessage();
            MyLogUtil.i("test", "APIException: "+ code + ErrorMessage.get(code));
            ToastUtils.showShort(message);

            if(code == 10005){//登录过期
                RxBus.getInstance().post(new RxBusEvent(700,"login_expired_from_default_subscriber"));
            }
        } else {
            if(viewModel!=null){
                viewModel.showErrorView();
            }
        }
        _onError(e);
    }

    protected abstract void _onNext(T t);

    protected abstract void _onError(Throwable e);

    protected abstract void _onCompleted();

    private void showProgressDialog() {
        if(showProgressDialog && dialog!=null){
            dialog.show();
        }
    }

    private void hideProgressDialog() {
        if(showProgressDialog && dialog!=null && dialog.isShowing()){
            dialog.dismiss();
        }
    }
}
