package com.example.multidagger.request;

import io.reactivex.Single;

public interface RequestClient {

    Single<String> getText();
    Single<Void> setText(String text);
}
