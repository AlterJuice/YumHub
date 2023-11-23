package com.alterjuice.network.pixabay.model.response

import com.alterjuice.network.pixabay.model.ImageQueryResponseItemDTO
import com.google.gson.annotations.SerializedName

data class ImagesQueryResponse(
    @SerializedName("hits") val hits: List<ImageQueryResponseItemDTO>,
    @SerializedName("total") val total: Int,
    @SerializedName("totalHits") val totalHits: Int
)
