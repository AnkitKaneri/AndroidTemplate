package com.github.ankitkaneri.androidtemplate.application;

import android.app.Application;

import com.github.ankitkaneri.androidtemplate.BuildConfig;

import butterknife.ButterKnife;

/**
 * Created by ankitkaneri on 27/05/18.
 */

public class MyApplication extends Application {
        @Override
        public void onCreate() {
            super.onCreate();
            if (BuildConfig.DEBUG) {
                ButterKnife.setDebug(true);
            }
        }
}
