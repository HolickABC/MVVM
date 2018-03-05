package com.xclib.base;

import android.databinding.ViewDataBinding;
import android.graphics.Bitmap;
import android.net.http.SslError;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.SslErrorHandler;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.just.library.AgentWeb;
import com.just.library.ChromeClientCallbackManager;
import com.xclib.R;
import com.xclib.mvvm.IViewModel;

/**
 * Created by xiongch on 2017/12/10.
 */

public abstract class WebBaseActivity<DB extends ViewDataBinding, VM extends IViewModel> extends BaseActivity<DB, VM> {

    protected AgentWeb agentWeb;
    public String mTitleName;
    public String mUrl;
    public View mCloseView;

    public WebBaseActivity(int layoutResID) {
        super(layoutResID);
    }

    public void onIvLeftClick(){
        if (!agentWeb.back()){
            onBackPressed();
        }
    }

    public void onCloseClick(){
        finish();
    }

    /**
     *
     * @param flWebContent  为WebView的父控件(不建议直接传根节点)
     */
    public void setupAgentWeb(FrameLayout flWebContent, View closeView) {
        mCloseView = closeView;

        agentWeb = AgentWeb.with(this)
                .setAgentWebParent(flWebContent,new LinearLayout.LayoutParams(-1,-1))
                .useDefaultIndicator()
                .setIndicatorColor(ContextCompat.getColor(this, R.color.mainColor))
                .setSecutityType(AgentWeb.SecurityType.strict)
                .setReceivedTitleCallback(mCallBack)
                .setWebViewClient(mWebViewClient)
                .createAgentWeb()
                .ready()
                .go(mUrl);
    }

    private ChromeClientCallbackManager.ReceivedTitleCallback mCallBack = new ChromeClientCallbackManager.ReceivedTitleCallback() {
        @Override
        public void onReceivedTitle(WebView view, String title) {

        }
    };

    /**
     * 尽量使用重写进行扩展  不建议使用抽象方法
     */
    private WebViewClient mWebViewClient = new WebViewClient(){

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            if(mCloseView == null){
                return;
            }
            if(mUrl.equals(url) || url.equals("") || url==null){
                mCloseView.setVisibility(View.INVISIBLE);
            }else {
                mCloseView.setVisibility(View.VISIBLE);
            }
        }

        @Override
        public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
            handler.proceed();
        }
    };

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (agentWeb!=null && agentWeb.handleKeyEvent(keyCode, event)) {
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onPause() {
        if(agentWeb!=null)
            agentWeb.getWebLifeCycle().onPause();
        super.onPause();

    }

    @Override
    protected void onResume() {
        if(agentWeb!=null)
            agentWeb.getWebLifeCycle().onResume();
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        if(agentWeb!=null)
            agentWeb.getWebLifeCycle().onDestroy();
        super.onDestroy();
    }

}
