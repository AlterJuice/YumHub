package com.alterjuice.network.pixabay.model

import com.google.gson.annotations.SerializedName

data class ImageQueryResponseItemDTO(
    @SerializedName("webformatHeight") val webformatHeight: Int,
    @SerializedName("imageWidth") val imageWidth: Int,
    @SerializedName("previewHeight") val previewHeight: Int,
    @SerializedName("webformatURL") val webformatURL: String,
    @SerializedName("userImageURL") val userImageURL: String,
    @SerializedName("previewURL") val previewURL: String,
    @SerializedName("comments") val comments: Int,
    @SerializedName("type") val type: String,
    @SerializedName("imageHeight") val imageHeight: Int,
    @SerializedName("tags") val tags: String,
    @SerializedName("previewWidth") val previewWidth: Int,
    @SerializedName("downloads") val downloads: Int,
    @SerializedName("collections") val collections: Int,
    @SerializedName("user_id") val userId: Int,
    @SerializedName("largeImageURL") val largeImageURL: String,
    @SerializedName("pageURL") val pageURL: String,
    @SerializedName("id") val id: Int,
    @SerializedName("imageSize") val imageSize: Int,
    @SerializedName("webformatWidth") val webformatWidth: Int,
    @SerializedName("user") val user: String,
    @SerializedName("views") val views: Int,
    @SerializedName("likes") val likes: Int
)
