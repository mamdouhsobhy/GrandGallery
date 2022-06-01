package com.example.grandgallery.core.data.utils

import com.example.grandgallery.core.presentation.common.SharedPrefs
import okhttp3.Interceptor
import okhttp3.Response

class RequestInterceptor constructor(private val pref: SharedPrefs) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val newRequest = chain.request().newBuilder()
            .addHeader("Accept", "application/json")
            //.addHeader("Authorization", "Bearer $token")
            .build()
        return chain.proceed(newRequest)
    }

}