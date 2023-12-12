package com.alterjuice.domain.model.nutrition

interface NutritionAttr {
    val attrID: Int
    val usdaTag: String
    val nutritionName: String
    val unit: String
    val importanceKey: Int get() = 999
}

data class NutritionAttrData(
    override val attrID: Int,
    override val usdaTag: String,
    override val nutritionName: String,
    override val unit: String,
    override val importanceKey: Int
): NutritionAttr