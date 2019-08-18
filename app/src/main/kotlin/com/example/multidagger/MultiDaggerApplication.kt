package com.example.multidagger

import com.example.multidagger.di.DaggerAppComponent
import com.example.multidagger.di.applyAutoInjector
import dagger.android.DaggerApplication

class MultiDaggerApplication : DaggerApplication() {

    override fun applicationInjector() = DaggerAppComponent.factory()
            .create(this)

    override fun onCreate() {
        super.onCreate()
        applyAutoInjector()
    }
}
