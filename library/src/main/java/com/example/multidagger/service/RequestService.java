package com.example.multidagger.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

import dagger.android.AndroidInjection;

public class RequestService extends Service {
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
                return "Hello";
            }

            @Override
            public void setText(String text) {

            }
        };
    }
}
