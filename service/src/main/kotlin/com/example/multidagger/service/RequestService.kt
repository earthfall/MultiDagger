package com.example.multidagger.service

import android.app.Service
import android.content.Intent
import android.content.SharedPreferences
import android.os.IBinder
import androidx.core.content.edit

import dagger.android.AndroidInjection

import javax.inject.Inject

private const val KEY = "text"

class RequestService : Service() {

    @Inject
    internal lateinit var sharedPreferences: SharedPreferences

    override fun onCreate() {
        super.onCreate()
        AndroidInjection.inject(this)
    }

    override fun onBind(intent: Intent): IBinder? {
        return object : IRequest.Stub() {
            override fun getText() = sharedPreferences.getString(KEY, "Hello")

            override fun setText(text: String) = sharedPreferences.edit {
                putString(KEY, text)
            }
        }
    }
}
