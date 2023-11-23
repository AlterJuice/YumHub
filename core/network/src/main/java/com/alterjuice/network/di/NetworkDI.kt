package com.alterjuice.network.di

import com.alterjuice.android_utils.DIModulesHub
import com.alterjuice.network.nutritionix.api.NutritionixAPI
import com.alterjuice.network.nutritionix.interceptors.NutritionixAuthInterceptor
import com.alterjuice.network.pixabay.api.PixabayAPI
import com.alterjuice.network.pixabay.iterceptors.PixabayTokenInterceptor
import okhttp3.OkHttpClient
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory

object NetworkDI: DIModulesHub {
    override fun modules() = arrayOf(
        pixabayAPI(),
        nutritionixAPI()
    )

    private fun pixabayAPI() = module {
        single<PixabayAPI> {
            val client = OkHttpClient.Builder()
                .addInterceptor(PixabayTokenInterceptor())
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

    private fun nutritionixAPI() = module {
        single<NutritionixAPI> {
            val client = OkHttpClient.Builder()
                .addInterceptor(NutritionixAuthInterceptor())
                .build()
            Retrofit.Builder()
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .addConverterFactory(ScalarsConverterFactory.create())
                .baseUrl("https://trackapi.nutritionix.com/")
                .build()
                .create(NutritionixAPI::class.java)
        }
    }
}