package com.alterjuice.network.pixabay.api


import com.alterjuice.network.pixabay.model.response.ImagesQueryResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface PixabayAPI {

    @GET("https://pixabay.com/api/")
    suspend fun getImageByQuery(
        @Query("q") q: String,
        @Query("orientation") orientation: String = "horizontal",
    ): ImagesQueryResponse
}