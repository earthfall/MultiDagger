package com.example.multidagger.request

import io.reactivex.Single

interface RequestClient {

    val text: Single<String>
    fun setText(text: String): Single<Void>
}
