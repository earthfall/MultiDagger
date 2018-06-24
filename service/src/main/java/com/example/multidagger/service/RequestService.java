package com.example.multidagger.service;

import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.IBinder;
import android.support.annotation.Nullable;

import dagger.android.AndroidInjection;

import javax.inject.Inject;

public class RequestService extends Service {
    private static final String KEY = "text";

    @Inject
    SharedPreferences sharedPreferences;

    @Override
    public void onCreate() {
        super.onCreate();
        AndroidInjection.inject(this);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return new IRequest.Stub() {
            @Override
            public String getText() {
                return sharedPreferences.getString(KEY, "Hello");
            }

            @Override
            public void setText(String text) {
                sharedPreferences.edit().putString(KEY, text).apply();
            }
        };
    }
}
