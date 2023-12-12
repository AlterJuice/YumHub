package com.alterjuice.network.the_meal_db.response

import com.google.gson.annotations.SerializedName

data class TheMealDBMealsByCategoryDTO(
    @SerializedName("meals") val meals: List<TheMealDBMealDTO>? = null
)

data class TheMealDBMealDTO(
    @SerializedName("strMealThumb") val strMealThumb: String? = null,
    @SerializedName("idMeal") val idMeal: String? = null,
    @SerializedName("strMeal") val strMeal: String? = null
)