package com.alterjuice.network.xanthir

import com.google.gson.annotations.SerializedName

data class MealWithRecipeDTO(
    @SerializedName("instructions") val instructions: String,
    @SerializedName("fiber") val fiber: Int,
    @SerializedName("comments") val comments: String,
    @SerializedName("carbs") val carbs: Int,
    @SerializedName("source") val source: String,
    @SerializedName("calories") val calories: Int,
    @SerializedName("waittime") val waittime: Long,
    @SerializedName("tags") val tags: List<String>,
    @SerializedName("servings") val servings: Int,
    @SerializedName("protein") val protein: Int,
    @SerializedName("name") val name: String,
    @SerializedName("fat") val fat: Int,
    @SerializedName("cooktime") val cooktime: Long,
    @SerializedName("satfat") val satfat: Int,
    @SerializedName("ingredients") val ingredients: List<String>,
    @SerializedName("id") val id: String,
    @SerializedName("sugar") val sugar: Int,
    @SerializedName("preptime") val preptime: Long
)