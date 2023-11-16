package com.alterjuice.network.pixabay.iterceptors

import com.alterjuice.network.BuildConfig
import okhttp3.Interceptor
import okhttp3.Response

class TokenInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        var request = chain.request()
        val url = request.url.newBuilder().addQueryParameter("key", BuildConfig.PIXABAY_API_KEY).build()
        request = request.newBuilder().url(url).build()
        return chain.proceed(request)
    }
}