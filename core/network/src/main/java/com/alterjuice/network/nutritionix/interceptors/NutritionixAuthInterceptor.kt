package com.alterjuice.network.nutritionix.interceptors


import com.alterjuice.network.BuildConfig
import okhttp3.Interceptor
import okhttp3.Response

class NutritionixAuthInterceptor: Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        var request = chain.request()
        request = request.newBuilder()
            .addHeader("x-app-id", BuildConfig.NUTRITIONIX_APP_ID) // Your app ID issued from developer.nutritionix.com)
            .addHeader("x-app-key", BuildConfig.NUTRITIONIX_APP_KEY) // Your app key issued from developer.nutritionix.com)
            .addHeader("x-remote-user-id", "0")
            .url(request.url)
            .build()
        return chain.proceed(request)
    }
}