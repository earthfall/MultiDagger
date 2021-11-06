package com.example.multidagger.request

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.IBinder
import android.util.Log
import com.example.multidagger.service.IRequest
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

private const val TAG = "TestRequest"

suspend fun Context.bindServiceAndWait(): BoundService = suspendCoroutine { continuation ->
    val intent = Intent("com.example.multidagger.intent.action.Request").apply {
        setPackage(packageName)
    }

    val conn = object: ServiceConnection {
        override fun onServiceConnected(name: ComponentName, service: IBinder) {
            continuation.resume(
                BoundService(this@bindServiceAndWait, name, IRequest.Stub.asInterface(service), this)
            )
        }

        // ignore, not much we can do
        override fun onServiceDisconnected(name: ComponentName) = Unit
    }

    bindService(intent, conn, Context.BIND_AUTO_CREATE)
}

class BoundService(
    private val context: Context,
    val name: ComponentName,
    private val service: IRequest,
    private val conn: ServiceConnection
): IRequest by service, AutoCloseable {

    override fun close() {
        Log.d(TAG, "Cleaning up service connection")
        context.unbindService(conn)
    }
}