package com.alterjuice.network.nutritionix.model.response

import com.google.gson.annotations.SerializedName

data class SearchInstantDTO(
	@SerializedName("branded") val branded: List<MealItemDTO.BrandedDTO>?,
	@SerializedName("common") val common: List<MealItemDTO.CommonDTO>?,
	@SerializedName("self") val self: List<MealItemDTO.SelfDTO>?
)

sealed interface MealItemDTO {
	val foodName: String?

	data class BrandedDTO(
		@SerializedName("food_name") override val foodName: String?,
		@SerializedName("image") val image: Any?,
		@SerializedName("serving_unit") val servingUnit: String?,
		@SerializedName("nix_brand_id") val nixBrandId: String?,
		@SerializedName("brand_name_item_name") val brandNameItemName: String?,
		@SerializedName("serving_qty") val servingQty: Int?,
		@SerializedName("nf_calories") val nfCalories: Int?,
		@SerializedName("brand_name") val brandName: String?,
		@SerializedName("brand_type") val brandType: Int?,
		@SerializedName("nix_item_id") val nixItemId: String?
	): MealItemDTO

	data class SelfDTO(
		@SerializedName("food_name") override val foodName: String?,
		@SerializedName("serving_unit") val servingUnit: String?,
		@SerializedName("nix_brand_id") val nixBrandId: Any?,
		@SerializedName("serving_qty") val servingQty: Int?,
		@SerializedName("nf_calories") val nfCalories: Any?,
		@SerializedName("brand_name") val brandName: Any?,
		@SerializedName("uuid") val uuid: String?,
		@SerializedName("nix_item_id") val nixItemId: Any?
	): MealItemDTO

	data class CommonDTO(
		@SerializedName("food_name") override val foodName: String?,
		@SerializedName("image") val image: String?,
		@SerializedName("tag_name") val tagName: String?,
		@SerializedName("tag_id") val tagId: String?
	): MealItemDTO
}

