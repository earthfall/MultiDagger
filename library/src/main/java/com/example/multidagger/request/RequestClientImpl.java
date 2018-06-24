package com.example.multidagger.request;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;

import com.example.multidagger.service.IRequest;

import io.reactivex.Single;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.subjects.BehaviorSubject;
import io.reactivex.subjects.PublishSubject;

// TODO: ServiceConnection has leakage
public class RequestClientImpl implements RequestClient, AutoCloseable {
    private static final String TAG = "TestRequest";

    private final BehaviorSubject<IRequest> serviceSubject = BehaviorSubject.create();
    private final CompositeDisposable disposables = new CompositeDisposable();

    private final ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            serviceSubject.onNext(IRequest.Stub.asInterface(service));
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            serviceSubject.onComplete();
        }
    };

    private final Context context;

    public RequestClientImpl(Context context) {
        this.context = context;

        Intent intent = new Intent("com.example.multidagger.intent.action.Request");
        intent.setPackage(context.getPackageName());
        context.bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE);
    }

    @Override
    public Single<String> getText() {
        final PublishSubject<String> subject = PublishSubject.create();
        Disposable disposable = serviceSubject.subscribe(service -> {
            try {
                subject.onNext(service.getText());
            } catch (RemoteException e) {
                subject.onError(e);
            }
        }, subject::onError);

        disposables.add(disposable);
        return subject.firstOrError();
    }

    @Override
    public Single<Void> setText(String text) {
        final PublishSubject<Void> subject = PublishSubject.create();
        Disposable disposable = serviceSubject.subscribe(service -> {
            try {
                service.setText(text);
                subject.onComplete();
            } catch (RemoteException e) {
                subject.onError(e);
            }
        }, subject::onError);

        disposables.add(disposable);
        return subject.firstOrError();
    }

    @Override
    public void close() {
        Log.d(TAG, "Cleaning up service connection");

        context.unbindService(serviceConnection);
        if (!disposables.isDisposed()) {
            disposables.dispose();
        }
    }
}
