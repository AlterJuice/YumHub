package com.alterjuice.network

import com.alterjuice.domain.model.common.Branded
import com.alterjuice.domain.model.common.CookInfo
import com.alterjuice.domain.model.common.MealCategories
import com.alterjuice.domain.model.common.Serving
import com.alterjuice.domain.model.common.YumHubMeal
import com.alterjuice.domain.model.nutrition.NutrientsItem
import com.alterjuice.domain.model.nutrition.NutritionEnum
import com.alterjuice.network.nutritionix.model.response.NutrientFoodItemAltMeasuresDTO
import com.alterjuice.network.nutritionix.model.response.NutrientFoodItemDTO
import com.alterjuice.network.nutritionix.model.response.NutrientFoodNutrientsDTO
import com.alterjuice.network.xanthir.MealWithRecipeDTO

fun NutrientFoodNutrientsDTO.toNutrient(): NutrientsItem? {
    return this.attrId?.let { NutritionEnum.map[it] }?.let {
        NutrientsItem(it, this.value)
    }
}

fun List<NutrientFoodItemAltMeasuresDTO>.toServingables()
    = this.map { it.toServingable() }

fun NutrientFoodItemAltMeasuresDTO.toServingable() = Serving(
    servingQty = this.qty,
    servingUnit = this.measure,
    servingWeightGrams = this.servingWeight
)

fun List<NutrientFoodNutrientsDTO>.toNutrients(): List<NutrientsItem> {
    return this.mapNotNull { it.toNutrient() }
}

fun NutrientFoodItemDTO.toYumHubMeal() = YumHubMeal.NutritionIXMeal(
    id = foodName.orEmpty(),
    foodName = foodName.orEmpty(),
    nutrients = fullNutrients?.toNutrients()?: emptyList(),
    calories = nfCalories?: 0.0,
    cookInfo = null,
    servings = altMeasures?.toServingables()?: emptyList(),
    branded = Branded(
        brandName = brandName,
        nixBrandName = nixBrandName
    ),
    categoriesTags = listOf(),
    servingsCount = servingQty?: 1.0
)

fun MealWithRecipeDTO.toYumHubMeal() = YumHubMeal.TheMealDBItem(
    id = this.id,
    foodName = this.name,
    nutrients = YumHubMeal.baseNutrientsFrom(
        fiber = fiber,
        carbs = carbs,
        protein = protein,
        fat = fat,
        satfat = satfat,
        sugar = sugar,
        calories = calories
    ),
    calories = calories.toDouble(),
    cookInfo = CookInfo(
        instructions = instructions,
        prepareTime = preptime,
        cookTime = cooktime,
        waitTime = waittime,
        ingredients = ingredients,
    ),
    servings = emptyList(),
    branded = null,
    categoriesTags = tags.flatMap { MealCategories.findTagsByContent(it) },
    servingsCount = servings.toDouble()
)
