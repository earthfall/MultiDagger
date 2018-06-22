package com.example.multidagger.di;

import android.app.Application;

import com.example.multidagger.MultiDaggerApplication;


import dagger.Binds;
import dagger.Module;

@Module
abstract class AppModule {

    @Binds
    abstract Application application(MultiDaggerApplication application);
}
