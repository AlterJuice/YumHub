package com.alterjuice.network.nutritionix.model.response

import com.google.gson.annotations.SerializedName

data class NutrientsDTO(
	@SerializedName("foods") val foods: List<NutrientFoodItemDTO>? = null
)

data class NutrientFoodItemDTO(
	@SerializedName("food_name") val foodName: String? = null,
	@SerializedName("metadata") val metadata: NutrientFoodItemMetadataDTO? = null,
	@SerializedName("nix_brand_id") val nixBrandId: Any? = null,
	@SerializedName("meal_type") val mealType: Int? = null,
	@SerializedName("source") val source: Int? = null,
	@SerializedName("nix_item_id") val nixItemId: Any? = null,
	@SerializedName("ndb_no") val ndbNo: Int? = null,
	@SerializedName("serving_unit") val servingUnit: String? = null,
	@SerializedName("alt_measures") val altMeasures: List<NutrientFoodItemAltMeasuresDTO>? = null,
	@SerializedName("nix_item_name") val nixItemName: Any? = null,
	@SerializedName("photo") val photo: NutrientFoodItemPhotoDTO? = null,
	@SerializedName("brand_name") val brandName: String? = null,
	@SerializedName("serving_weight_grams") val servingWeightGrams: Double? = null,
	@SerializedName("full_nutrients") val fullNutrients: List<NutrientFoodNutrientsDTO>? = null,
	@SerializedName("tags") val tags: NutrientFoodTagDTO?,
	@SerializedName("nix_brand_name") val nixBrandName: String? = null,
	@SerializedName("serving_qty") val servingQty: Double? = null,
	@SerializedName("nf_calories") val nfCalories: Double? = null,
)

data class NutrientFoodTagDTO(
	@SerializedName("item") val item: String? = null,
	@SerializedName("measure") val measure: String? = null,
	@SerializedName("quantity") val quantity: String? = null,
	@SerializedName("tag_id") val tagId: Int? = null
)

data class NutrientFoodNutrientsDTO(
	@SerializedName("value") val value: Double? = null,
	@SerializedName("attr_id") val attrId: Int? = null
)

data class NutrientFoodItemAltMeasuresDTO(
	@SerializedName("serving_weight") val servingWeight: Double? = null,
	@SerializedName("measure") val measure: String? = null,
	@SerializedName("qty") val qty: Double? = null,
	@SerializedName("seq") val seq: Int? = null
)

data class NutrientFoodItemMetadataDTO(
	val any: Any? = null
)

data class NutrientFoodItemPhotoDTO(
	@SerializedName("thumb") val thumb: String? = null,
	@SerializedName("highres") val highres: String? = null
)