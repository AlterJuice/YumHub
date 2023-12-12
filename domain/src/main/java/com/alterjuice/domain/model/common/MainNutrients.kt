package com.alterjuice.domain.model.common

import com.alterjuice.domain.model.nutrition.NutritionEnum


interface MainNutrients<T> {
    val fiber: T
    val carbs: T
    val protein: T
    val fat: T
    val fatSat: T
    val sugars: T
    val sugarsAdded: T
    val sodiumNa: T
    val cholesterol: T
    val energyCalories: T
    val iron: T
    val potassium: T
    val calcium: T
    val vitaminD: T
    val caffeine: T

    val mainNutrients get() = listOf(
        energyCalories, fiber, carbs, protein,
        fat, fatSat, sugars, sugarsAdded, sodiumNa, iron, cholesterol,
        potassium, calcium, vitaminD, caffeine
    )
}

fun <T> MainNutrients<T>.by(nutrient: NutritionEnum): T? {
    return when (nutrient) {
        NutritionEnum.EnergyCalories -> energyCalories
        NutritionEnum.Protein -> protein
        NutritionEnum.Fat -> fat
        NutritionEnum.FatSat -> fatSat
        NutritionEnum.Carbs -> carbs
        NutritionEnum.Calcium -> calcium
        NutritionEnum.Sugars -> sugars
        NutritionEnum.SugarsAdded -> sugarsAdded
        NutritionEnum.SodiumNa -> sodiumNa
        NutritionEnum.Iron -> iron
        NutritionEnum.Fiber -> fiber
        NutritionEnum.Caffeine -> caffeine
        NutritionEnum.Cholesterol -> cholesterol
        NutritionEnum.Potassium -> potassium
        NutritionEnum.VitaminD -> vitaminD
        else -> null
    }
}