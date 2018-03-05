package com.xclib.http;


import com.blankj.utilcode.util.NetworkUtils;
import com.franmontiel.persistentcookiejar.ClearableCookieJar;
import com.franmontiel.persistentcookiejar.PersistentCookieJar;
import com.franmontiel.persistentcookiejar.cache.SetCookieCache;
import com.franmontiel.persistentcookiejar.persistence.SharedPrefsCookiePersistor;
import com.xclib.base.BaseApplication;
import com.xclib.config.ProjectConfig;
import com.xclib.util.MyLogUtil;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by xiongchang on 17/6/27.
 */

public class RetrofitAsyncTask {
    private Retrofit retrofit;
    private static RetrofitAsyncTask instance;
    private static OkHttpClient client;
    private static final int DEFAULT_TIME_OUT = 10;//默认的超时时间5秒钟
    // 设缓存有效期为两天
    private static final long CACHE_STALE_SEC = 60 * 60 * 24 * 2;
    // 30秒内直接读缓存
    private static final long CACHE_AGE_SEC = 0;

    public static synchronized RetrofitAsyncTask getInstance(){
        if (instance == null){
            instance=new RetrofitAsyncTask();
        }
        return instance;
    }

    public Retrofit getRetrofit(){
        if (retrofit == null){
            retrofit = new Retrofit.Builder()
                    .client(getTokenOkHttpClient())
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .baseUrl(ProjectConfig.getBaseUrl())
                    .build();
        }
        return retrofit;
    }

    private OkHttpClient getLogOkHttpClient(){
        if(client == null){
            synchronized (RetrofitAsyncTask.class){
                if(client == null){
                    // 指定缓存路径,缓存大小20Mb
                    /*Cache cache = new Cache(new File(ProjectConfig.DIR_CACHE, "HttpCache"), 1024 * 1024 * 20);
                    OkHttpClient.Builder builder = new OkHttpClient.Builder().cache(cache);
                    builder.connectTimeout(DEFAULT_TIME_OUT, TimeUnit.SECONDS);
                    builder.addInterceptor(loggingInterceptor);
                    builder.addNetworkInterceptor(mRewriteCacheControlInterceptor);
                    builder.addInterceptor(mRewriteCacheControlInterceptor);
                    client = builder.build();*/

                    //不使用缓存
                    OkHttpClient.Builder builder = new OkHttpClient.Builder();
                    builder.connectTimeout(DEFAULT_TIME_OUT, TimeUnit.SECONDS);
                    builder.addInterceptor(loggingInterceptor);
                    client = builder.build();
                }
            }
        }
        return client;
    }


    private OkHttpClient getTokenOkHttpClient() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.connectTimeout(DEFAULT_TIME_OUT, TimeUnit.SECONDS);

        if(ProjectConfig.DEBUG){
            builder.addInterceptor(loggingInterceptor);
        }

        //请求头转换content-type
        builder.addInterceptor(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request request = chain.request()
                        .newBuilder()
                        .addHeader("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8")
                        .build();
                return chain.proceed(request);
            }
        });

        //存储cookie
        ClearableCookieJar cookieJar =
                new PersistentCookieJar(new SetCookieCache(), new SharedPrefsCookiePersistor(BaseApplication.getInstance().getApplicationContext()));
        builder.cookieJar(cookieJar);

        //请求头里面添加token
//        builder.addInterceptor(new Interceptor() {
//            @Override
//            public Response intercept(Chain chain) throws IOException {
//                String token = SPUtil.getString("Token");
//
//                Request.Builder requestBuilder = chain.request().newBuilder();
//                if(token != null && !token.equals("")){
//                    //requestBuilder.header("Authorization", token);
//                    requestBuilder.header("Token", token);
//                }else {
//
//                }
//                Request authorised = requestBuilder.build();
//                return chain.proceed(authorised);
//            }
//        });

        //client = ProgressManager.getInstance().with(builder).build();

        client = builder.build();

        return client;
    }


    private HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor(
            new HttpLoggingInterceptor.Logger() {
                @Override
                public void log(String message) {
                    MyLogUtil.i("test","收到响应: " + message);
                }
            }).setLevel(HttpLoggingInterceptor.Level.BODY);


    // 云端响应头拦截器，用来配置缓存策略
    private Interceptor mRewriteCacheControlInterceptor = new Interceptor() {
        @Override
        public Response intercept(Chain chain) throws IOException {
            Request request = chain.request();
            // 在这里统一配置请求头缓存策略以及响应头缓存策略
            if (NetworkUtils.isConnected()) {
                // 在有网的情况下CACHE_AGE_SEC秒内读缓存，大于CACHE_AGE_SEC秒后会重新请求数据
                request = request.newBuilder().removeHeader("Pragma").removeHeader("Cache-Control").header("Cache-Control", "public, max-age=" + CACHE_AGE_SEC).build();
                Response response = chain.proceed(request);
                return response.newBuilder().removeHeader("Pragma").removeHeader("Cache-Control").header("Cache-Control", "public, max-age=" + CACHE_AGE_SEC).build();
            } else {
                // 无网情况下CACHE_STALE_SEC秒内读取缓存，大于CACHE_STALE_SEC秒缓存无效报504
                request = request.newBuilder().removeHeader("Pragma").removeHeader("Cache-Control")
                        .header("Cache-Control", "public, only-if-cached, max-stale=" + CACHE_STALE_SEC).build();
                Response response = chain.proceed(request);
                return response.newBuilder().removeHeader("Pragma").removeHeader("Cache-Control")
                        .header("Cache-Control", "public, only-if-cached, max-stale=" + CACHE_STALE_SEC).build();
            }

        }
    };

}
