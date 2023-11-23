package com.alterjuice.domain.model.common

import com.alterjuice.domain.model.nutrition.NutrientsItem
import com.alterjuice.domain.model.nutrition.NutritionEnum
import com.alterjuice.domain.model.nutrition.toNutrients


data class CookInfo(
    val instructions: String? = null,
    val prepareTime: Int? = null,
    val cookTime: Int? = null,
    val waitTime: Int? = null,
    val ingredients: List<String> = emptyList(),
)

data class Serving(
    val servingQty: Double?,
    val servingUnit: String?,
    val servingWeightGrams: Double?,
)

data class Branded(
    val brandName: String? = null,
    val nixBrandName: String? = null
)



sealed class YumHubMeal(val type: String) {
    abstract val id: String
    abstract val foodName: String
    abstract val nutrients: List<NutrientsItem>
    abstract val calories: Double
    abstract val cookInfo: CookInfo?
    abstract val servings: List<Serving>
    abstract val branded: Branded?
    abstract val categoriesTags: List<MealCategories>


    class NutritionIXMeal(
        override val id: String,
        override val foodName: String,
        override val nutrients: List<NutrientsItem> = emptyList(),
        override val calories: Double,
        override val cookInfo: CookInfo?,
        override val servings: List<Serving> = emptyList(),
        override val branded: Branded?,
        override val categoriesTags: List<MealCategories> = emptyList(),
    ): YumHubMeal("nutritionix") // https://www.nutritionix.com/food/bacon



    class TheMealDBItem(
        override val id: String,
        override val foodName: String,
        override val nutrients: List<NutrientsItem> = emptyList(),
        override val calories: Double,
        override val cookInfo: CookInfo,
        override val servings: List<Serving> = emptyList(),
        override val branded: Branded?,
        override val categoriesTags: List<MealCategories> = emptyList()
    ): YumHubMeal("local_database") {
    }

    fun getDescription() = buildString {
        appendLine(foodName).appendLine().appendLine()
        appendLine(cookable?.ingredients?.joinToString("\n"))
        appendLine().appendLine().appendLine(cookable?.instructions)
    }



    companion object {

        fun empty(name: String) = YumHubMeal.TheMealDBItem(
            id = "Empty${System.currentTimeMillis()}",
            foodName = name,
            nutrients = baseNutrientsFrom(),
            calories = 0.0,
            cookInfo = CookInfo(
                instructions = null,
                prepareTime = null,
                cookTime = null,
                waitTime = null,
                ingredients = emptyList()
            ),
            servings = emptyList(),
            branded = null,
            categoriesTags = emptyList()
        )


        fun baseNutrientsFrom(
            fiber: Number? = null,
            carbs: Number? = null,
            protein: Number? = null,
            fat: Number? = null,
            satfat: Number? = null,
            sugar: Number? = null,
            sodium: Number? = null,
            cholesterol: Number? = null,
            calories: Number? = null,
        ): List<NutrientsItem> = listOfNotNull(
            NutritionEnum.Fiber to fiber,
            NutritionEnum.Carbs to carbs,
            NutritionEnum.Protein to protein,
            NutritionEnum.Fat to fat,
            NutritionEnum.FatSat to satfat,
            NutritionEnum.Sugars to sugar,
            NutritionEnum.SodiumNa to sodium,
            NutritionEnum.Cholesterol to cholesterol,
            NutritionEnum.EnergyCalories to calories
        ).toNutrients()
    }
}