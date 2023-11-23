package com.alterjuice.network.nutritionix.api

import com.alterjuice.network.nutritionix.model.request.NutrientsRequest
import com.alterjuice.network.nutritionix.model.response.NutrientsDTO
import com.alterjuice.network.nutritionix.model.response.SearchInstantDTO
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface NutritionixAPI {

    @GET("/v2/search/instant")
    suspend fun proceedAutocompleteSearch(
        @Query("query") query: String
    ): SearchInstantDTO

    @POST("v2/natural/nutrients")
    suspend fun getNutrients(
        @Body query: NutrientsRequest
    ): NutrientsDTO
}

