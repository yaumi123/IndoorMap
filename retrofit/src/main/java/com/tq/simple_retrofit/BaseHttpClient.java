package com.tq.simple_retrofit;

import android.util.Log;

import com.google.gson.GsonBuilder;
import com.tq.simple_retrofit.progress.ProgressConverterFactory;
import com.tq.simple_retrofit.progress.ProgressInterceptor;
import com.tq.simple_retrofit.progress.ProgressListenerPool;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import io.reactivex.disposables.Disposable;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;


/**
 * Created by tony on 2017/1/11.
 */

public abstract class BaseHttpClient<T> {
    private final static String TAG = BaseHttpClient.class.getSimpleName();

    private static final int DEFAULT_TIMEOUT = 15;

    private T mService;
    private Disposable mDisposable;

    public BaseHttpClient(Class<T> cls, String baseUrl) {
        this(cls, baseUrl, DEFAULT_TIMEOUT);
    }

    public BaseHttpClient(Class<T> cls, int timeout) {
        this(cls, DefRetrofit.getDef().getBASE_URL(), timeout);
    }

    public BaseHttpClient(Class<T> cls) {
        this(cls, DefRetrofit.getDef().getBASE_URL(), DEFAULT_TIMEOUT);
    }

    public BaseHttpClient(Class<T> cls, String baseUrl, int timeout) {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.connectTimeout(timeout, TimeUnit.SECONDS);
        builder.writeTimeout(timeout, TimeUnit.SECONDS);
        builder.readTimeout(timeout, TimeUnit.SECONDS);

        ProgressListenerPool listenerPool = new ProgressListenerPool();
        builder.addInterceptor(new ProgressInterceptor(listenerPool));

        setHttpClient(builder);

        Retrofit.Builder retrofitBuilder =
                new Retrofit.Builder();

        setRetrofit(retrofitBuilder);

        retrofitBuilder
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(new ProgressConverterFactory(listenerPool));


        Log.d(TAG, "BaseHttpClient: "+baseUrl);

        mService = retrofitBuilder
                .client(builder.build())
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory
                        .create(addGsonConverterFactory().create()))
                .baseUrl(baseUrl)
                .build()
                .create(cls);
    }


    protected GsonBuilder addGsonConverterFactory() {
        return new GsonBuilder();
    }

    protected void setRetrofit(Retrofit.Builder builder) {

    }

    protected void setHttpClient(OkHttpClient.Builder builder) {

        /**
         * 添加headers
         */
        Interceptor addHeadersInterceptor = new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request request = addHeaders(chain.request().newBuilder());
                Response response = chain.proceed(request);
                //存入Session
                if (response.header("Set-Cookie") != null) {
                    Log.d(TAG, "intercept: " + response.header("Set-Cookie"));
                }
                return response;
            }
        };
        builder.addInterceptor(addHeadersInterceptor);

        if (addLogging()) {
            /**
             * 设置日志
             */
            HttpLoggingInterceptor loggerInterceptor = new HttpLoggingInterceptor();
            loggerInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            builder.addInterceptor(loggerInterceptor);
        }

    }

    protected Request addHeaders(Request.Builder builder) {
        builder.addHeader("platform", "0");
        //   builder.addHeader("Accept", "application/json");
        return builder.build();
    }


    protected T service() {
        return mService;
    }


    public void setDisposable(Disposable disposable) {
        this.mDisposable = disposable;
    }

    protected boolean addLogging() {
        return true;
    }


    /**
     * 取消订阅，防止数据返回时，用户已退出当前activity而造成的APP闪退bug
     */
    public void cancel() {
        if (mDisposable != null && !mDisposable.isDisposed())
            mDisposable.dispose();
    }
}
