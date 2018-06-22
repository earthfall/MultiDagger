package com.example.multidagger.di;

import com.example.multidagger.service.RequestService;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
abstract class ServiceBuildersModule {

    @ContributesAndroidInjector
    abstract RequestService contributeRequestService();
}
