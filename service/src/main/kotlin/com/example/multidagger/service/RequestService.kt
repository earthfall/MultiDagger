package com.example.multidagger.service

import android.app.Service
import android.content.Intent
import android.content.SharedPreferences
import android.os.IBinder
import androidx.core.content.edit
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

private const val KEY = "text"

@AndroidEntryPoint
class RequestService : Service() {

    @Inject
    internal lateinit var sharedPreferences: SharedPreferences

    override fun onBind(intent: Intent): IBinder = object : IRequest.Stub() {
        override fun getText() = sharedPreferences.getString(KEY, "Hello")

        override fun setText(text: String) = sharedPreferences.edit {
            putString(KEY, text)
        }
    }
}
