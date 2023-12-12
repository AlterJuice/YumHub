package com.alterjuice.domain.model.nutrition

data class NutrientsItem(
	val attr: NutritionAttr,
	val value: Double?
)

val NutrientsItem?.valueOrZero get() = this?.value?: 0.0


fun List<Pair<NutritionAttr, Number?>>.toNutrients()
	= this.map { NutrientsItem(it.first, it.second?.toDouble()) }

fun List<NutrientsItem>.by(attr: NutritionAttr): NutrientsItem? {
	return this.find { it.attr.attrID == attr.attrID }
}