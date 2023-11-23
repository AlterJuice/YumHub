package com.alterjuice.network.nutritionix.model.request

import com.google.gson.annotations.SerializedName

data class NutrientsRequest(
    @SerializedName("query") val query: String
)