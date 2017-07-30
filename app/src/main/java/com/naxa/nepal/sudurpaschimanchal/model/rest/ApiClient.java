package com.naxa.nepal.sudurpaschimanchal.model.rest;

import com.github.simonpercic.oklog3.OkLogInterceptor;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.schedulers.Schedulers;


public class ApiClient {

    private static final String BASE_URL = "http:/www.naxa.com.np/buildchange/ws/";
    private static Retrofit retrofit = null;
    private static RxJavaCallAdapterFactory rxAdapter = RxJavaCallAdapterFactory.createWithScheduler(Schedulers.io());

    public static Retrofit getClient() {


        OkHttpClient okHttpClient = buildOkLogInterceptor();

        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL).client(okHttpClient)
                    .addConverterFactory(GsonConverterFactory.create()).addCallAdapterFactory(rxAdapter)
                    .build();
        }
        return retrofit;
    }


    private static OkHttpClient buildOkLogInterceptor() {
        OkLogInterceptor okLogInterceptor = OkLogInterceptor.builder().build();
        OkHttpClient.Builder okHttpBuilder = new OkHttpClient.Builder();
        okHttpBuilder.addInterceptor(okLogInterceptor);
        OkHttpClient okHttpClient = okHttpBuilder.build();
        return okHttpClient;
    }


}