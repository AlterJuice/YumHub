package com.alterjuice.domain.model.common

import com.alterjuice.domain.model.nutrition.NutrientsItem
import com.alterjuice.domain.model.nutrition.NutritionAttr
import com.alterjuice.domain.model.nutrition.NutritionEnum
import com.alterjuice.domain.model.nutrition.by
import com.alterjuice.domain.model.nutrition.toNutrients
import com.alterjuice.domain.model.nutrition.valueOrZero
import com.alterjuice.utils.extensions.gracefulRound
import com.alterjuice.utils.extensions.toHex
import java.security.MessageDigest


data class CookInfo(
    val instructions: String? = null,
    val prepareTime: Long? = null,
    val cookTime: Long? = null,
    val waitTime: Long? = null,
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



sealed class YumHubMeal(val type: String): MainNutrients<NutrientsItem?> {
    abstract val id: String
    abstract val foodName: String
    abstract val nutrients: List<NutrientsItem>
    abstract val calories: Double
    abstract val cookInfo: CookInfo?
    abstract val servings: List<Serving>
    abstract val servingsCount: Double
    abstract val branded: Branded?
    abstract val categoriesTags: List<MealCategories>

    @delegate:Transient
    val hash by lazy {
        MessageDigest
            .getInstance("MD5")
            .digest((id + foodName).toString().toByteArray()).toHex()
    }

    @delegate:Transient override val fiber by lazy { nutrientBy(NutritionEnum.Fiber) }
    @delegate:Transient override val carbs by lazy { nutrientBy(NutritionEnum.Carbs) }
    @delegate:Transient override val protein by lazy { nutrientBy(NutritionEnum.Protein) }
    @delegate:Transient override val fat by lazy { nutrientBy(NutritionEnum.Fat) }
    @delegate:Transient override val fatSat by lazy { nutrientBy(NutritionEnum.FatSat) }
    @delegate:Transient override val sugars by lazy { nutrientBy(NutritionEnum.Sugars) }
    @delegate:Transient override val sugarsAdded by lazy { nutrientBy(NutritionEnum.SugarsAdded) }
    @delegate:Transient override val sodiumNa by lazy { nutrientBy(NutritionEnum.SodiumNa) }
    @delegate:Transient override val cholesterol by lazy { nutrientBy(NutritionEnum.Cholesterol) }
    @delegate:Transient override val energyCalories by lazy { nutrientBy(NutritionEnum.EnergyCalories) }
    @delegate:Transient override val iron by lazy { nutrientBy(NutritionEnum.Iron) }
    @delegate:Transient override val potassium by lazy { nutrientBy(NutritionEnum.Potassium) }
    @delegate:Transient override val calcium by lazy { nutrientBy(NutritionEnum.Calcium) }
    @delegate:Transient override val vitaminD by lazy { nutrientBy(NutritionEnum.VitaminD) }
    @delegate:Transient override val caffeine by lazy { nutrientBy(NutritionEnum.Caffeine) }


    override val mainNutrients: List<NutrientsItem?> get() = super.mainNutrients

    fun withOneServing(nutrition: NutritionAttr): Double {
        return withServings(1.0, nutrition)
    }

    fun withServings(servingsCount: Double, nutrition: NutritionAttr): Double {
        val nutrient = nutrientBy(nutrition)
        val nutrientValue = nutrient.valueOrZero
        val oneServingNutrients = nutrientValue / this.servingsCount
        return servingsCount * oneServingNutrients
    }

    fun withServingsWithUnits(
        servingsCount: Double,
        nutrition: NutritionAttr,
    ): String {
        return withServingsTransform(servingsCount, nutrition) { attr, value ->
            "${value.gracefulRound()} ${attr.unit}"
        }
    }

    fun withServingsTransform(
        servingsCount: Double,
        nutrition: NutritionAttr,
        transform: (NutritionAttr, value: Double) -> String
    ): String {
        return transform(nutrition, withServings(servingsCount, nutrition))
    }

    data class NutritionIXMeal(
        override val id: String,
        override val foodName: String,
        override val nutrients: List<NutrientsItem> = emptyList(),
        override val calories: Double,
        override val cookInfo: CookInfo?,
        override val servings: List<Serving> = emptyList(),
        override val servingsCount: Double,
        override val branded: Branded?,
        override val categoriesTags: List<MealCategories> = emptyList(),
    ): YumHubMeal("nutritionix") // https://www.nutritionix.com/food/bacon

    data class TheMealDBItem(
        override val id: String,
        override val foodName: String,
        override val nutrients: List<NutrientsItem> = emptyList(),
        override val calories: Double,
        override val cookInfo: CookInfo,
        override val servings: List<Serving> = emptyList(),
        override val servingsCount: Double,
        override val branded: Branded?,
        override val categoriesTags: List<MealCategories> = emptyList(),
    ): YumHubMeal("local_database")

    fun getDescription() = buildString {
        appendLine(foodName).appendLine().appendLine()
        appendLine(cookInfo?.ingredients?.joinToString("\n"))
        appendLine().appendLine().appendLine(cookInfo?.instructions)
    }

    fun nutrientBy(nutrient: NutritionAttr): NutrientsItem? {
        return this.nutrients.by(nutrient)
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
            categoriesTags = emptyList(),
            servingsCount = 1.0,
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