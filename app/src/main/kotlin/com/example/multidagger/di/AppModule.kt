package com.example.multidagger.di

import android.app.Application

import android.content.Context
import android.content.SharedPreferences

import dagger.Module
import dagger.Provides

import javax.inject.Singleton

@Module
internal object AppModule {

    @Singleton
    @JvmStatic
    @Provides
    fun providesSharedPreference(application: Application): SharedPreferences {
        return application.getSharedPreferences("test", Context.MODE_PRIVATE)
    }
}
