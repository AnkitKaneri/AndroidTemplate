package com.github.ankitkaneri.androidtemplate.di;

import android.content.Context;

import com.github.ankitkaneri.androidtemplate.BuildConfig;
import com.github.ankitkaneri.androidtemplate.network.MyApi;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.concurrent.TimeUnit;

import dagger.Provides;
import okhttp3.CertificatePinner;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by ankitkaneri on 27/05/18.
 */

public class ApiModule {

    private static final int TIMEOUT = 3;

    @Provides
    @ApplicationScope
    public static MyApi provideSortlyApi(OkHttpClient okHttpClient) {
        final Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl(BuildConfig.SERVER_URL)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create(provideGson()))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create());
        return builder.build().create(MyApi.class);
    }


    @Provides
    @ApplicationScope
    public static OkHttpClient provideOkHttpClient(RequestInterceptor requestInterceptor) {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        if (BuildConfig.DEBUG) {
            interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        } else {
            interceptor.setLevel(HttpLoggingInterceptor.Level.NONE);
        }

        OkHttpClient.Builder okHttpBuilder = new OkHttpClient.Builder();
        if (BuildConfig.FLAVOR.equals(PRODUCTION)) {
            CertificatePinner certificatePinner = new CertificatePinner.Builder()
                    .add(BuildConfig.DOMAIN, PUBLIC_KEY)
                    .build();
            okHttpBuilder.certificatePinner(certificatePinner);

        }


        return okHttpBuilder.readTimeout(TIMEOUT, TimeUnit.MINUTES)
                .writeTimeout(TIMEOUT, TimeUnit.MINUTES)
                .connectTimeout(TIMEOUT, TimeUnit.MINUTES)
                .addInterceptor(requestInterceptor)
                .addInterceptor(interceptor).build();

    }

    @Provides
    @ApplicationScope
    public static RequestInterceptor provideRequestInterceptor(PreferenceUtils preferenceUtils,
                                                               Context context) {
        return new RequestInterceptor(preferenceUtils, context);
    }

    @Provides
    @ApplicationScope
    public static Gson provideGson() {
        return new GsonBuilder().serializeNulls().create();
    }

}