package com.james.weatherapplication

import okhttp3.Interceptor
import okhttp3.Response

class StubInterceptor() : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        return chain.proceed(chain.request())
    }
}