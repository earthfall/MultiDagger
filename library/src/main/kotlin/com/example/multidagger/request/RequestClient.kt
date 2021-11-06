package com.example.multidagger.request

interface RequestClient {

    suspend fun getText(): String
    suspend fun setText(text: String)
}
