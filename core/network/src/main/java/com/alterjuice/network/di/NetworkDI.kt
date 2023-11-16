package com.alterjuice.network.di

import com.alterjuice.network.pixabay.api.PixabayAPI
import com.alterjuice.network.pixabay.iterceptors.TokenInterceptor
import okhttp3.OkHttpClient
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory

object NetworkDI {
    fun moduleAPI() = module {
        single<PixabayAPI> {
            val client = OkHttpClient.Builder()
                .addInterceptor(TokenInterceptor())
                .build()
            Retrofit.Builder()
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .addConverterFactory(ScalarsConverterFactory.create())
                .baseUrl("https://pixabay.com/api/")
                .build()
                .create(PixabayAPI::class.java)
        }
    }
}