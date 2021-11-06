package com.example.multidagger.ui

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import com.example.multidagger.request.bindServiceAndWait
import kotlinx.coroutines.CoroutineName
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.currentCoroutineContext
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

private const val TAG = "TestActivity"

class MainActivity : AppCompatActivity(), LifecycleOwner {
    private val mainCoroutine = CoroutineName("TestCoroutine")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        runBlocking {
            lifecycleScope.launch(Dispatchers.Main + mainCoroutine) {
                val service = this@MainActivity.bindServiceAndWait()
                val text = service.text

                Log.d(TAG, "Received message from service: $text on ${currentCoroutineContext()[CoroutineName]?.name}")
            }
        }
    }
}
