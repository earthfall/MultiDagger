package com.example.multidagger.di

import com.example.multidagger.service.RequestService

import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
internal abstract class ServiceBuildersModule {

    @ContributesAndroidInjector
    internal abstract fun contributeRequestService(): RequestService
}
