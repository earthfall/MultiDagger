package com.example.multidagger.di

import android.app.Application
import com.example.multidagger.MultiDaggerApplication
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import dagger.android.AndroidInjector

import javax.inject.Singleton

@Singleton
@Component(modules = [AndroidInjectionModule::class, AppModule::class, ServiceBuildersModule::class])
interface AppComponent : AndroidInjector<MultiDaggerApplication> {

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance application: Application) : AppComponent
    }

    override fun inject(application: MultiDaggerApplication)
}
