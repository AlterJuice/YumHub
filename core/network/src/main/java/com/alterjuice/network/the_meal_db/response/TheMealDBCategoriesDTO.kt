package com.alterjuice.network.the_meal_db.response

import com.google.gson.annotations.SerializedName

data class TheMealDBCategoriesDTO(
    @SerializedName("categories") val categories: List<TheMealDBCategoryDTO> = emptyList()
)

data class TheMealDBCategoryDTO(
    @SerializedName("strCategory") val strCategory: String,
    @SerializedName("strCategoryDescription") val strCategoryDescription: String? = null,
    @SerializedName("idCategory") val idCategory: String? = null,
    @SerializedName("strCategoryThumb") val strCategoryThumb: String? = null
)