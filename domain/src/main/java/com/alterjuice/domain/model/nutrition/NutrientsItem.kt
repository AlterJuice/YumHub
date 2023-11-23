package com.alterjuice.domain.model.nutrition

data class NutrientsItem(
	val attr: NutritionAttr,
	val value: Double?
)


fun List<Pair<NutritionAttr, Number?>>.toNutrients()
	= this.map { NutrientsItem(it.first, it.second?.toDouble()) }

fun List<NutrientsItem>.by(attr: NutritionAttr): NutrientsItem? {
	return this.find { it.attr.attrID == attr.attrID }
}