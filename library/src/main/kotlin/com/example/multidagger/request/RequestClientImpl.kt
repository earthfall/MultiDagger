package com.example.multidagger.request

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.IBinder
import android.os.RemoteException
import android.util.Log

import com.example.multidagger.service.IRequest

import io.reactivex.Single
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.subjects.BehaviorSubject
import io.reactivex.subjects.PublishSubject

private const val TAG = "TestRequest"

// TODO: ServiceConnection has leakage
class RequestClientImpl(private val context: Context) : RequestClient, AutoCloseable {

    private val serviceSubject = BehaviorSubject.create<IRequest>()
    private val disposables = CompositeDisposable()

    private val serviceConnection = object : ServiceConnection {
        override fun onServiceConnected(name: ComponentName, service: IBinder) {
            serviceSubject.onNext(IRequest.Stub.asInterface(service))
        }

        override fun onServiceDisconnected(name: ComponentName) {
            serviceSubject.onComplete()
        }
    }

    override val text: Single<String>
        get() = with(PublishSubject.create<String>()) {
            disposables.add(serviceSubject.subscribe({
                try {
                    onNext(it.text)
                } catch (e: RemoteException) {
                    onError(e)
                }
            }, { onError(it) }))
            return firstOrError()
        }

    init {
        context.bindService(
            Intent("com.example.multidagger.intent.action.Request").apply {
                setPackage(context.packageName)
            },
            serviceConnection,
            Context.BIND_AUTO_CREATE
        )
    }

    override fun setText(text: String): Single<Void> = with(PublishSubject.create<Void>()) {
        disposables.add(serviceSubject.subscribe({
            try {
                it.text = text
                onComplete()
            } catch (e: RemoteException) {
                onError(e)
            }
        }, { onError(it) }))
        return firstOrError()
    }

    override fun close() {
        Log.d(TAG, "Cleaning up service connection")

        context.unbindService(serviceConnection)
        if (!disposables.isDisposed) {
            disposables.dispose()
        }
    }
}
