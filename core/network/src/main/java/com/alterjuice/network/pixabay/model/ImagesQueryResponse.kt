package com.alterjuice.network.pixabay.model

import com.google.gson.annotations.SerializedName

data class ImagesQueryResponse(
    @SerializedName("hits") val hits: List<ImageQueryResponseItemDTO>,
    @SerializedName("total") val total: Int,
    @SerializedName("totalHits") val totalHits: Int
)
