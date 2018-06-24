package com.example.multidagger.di;

import android.app.Application;

import android.content.Context;
import android.content.SharedPreferences;

import dagger.Module;
import dagger.Provides;

import javax.inject.Singleton;

@Module
abstract class AppModule {

    @Singleton
    @Provides
    static SharedPreferences providesSharedPreference(Application application) {
        return application.getSharedPreferences("test", Context.MODE_PRIVATE);
    }
}
