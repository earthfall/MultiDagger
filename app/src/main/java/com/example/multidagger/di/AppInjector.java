package com.example.multidagger.di;

import com.example.multidagger.MultiDaggerApplication;

public class AppInjector {
    private AppInjector() {
    }

    public static void init(MultiDaggerApplication application) {
        DaggerAppComponent.builder()
                .application(application)
                .build()
                .inject(application);
    }
}
