package com.alterjuice.domain.model

import com.alterjuice.domain.model.common.MealType
import com.alterjuice.domain.model.common.YumHubMeal

data class MealsHistory(
    val dateTimestampMs: Long,
    val mealType: MealType,
    val yumHubMeal: YumHubMeal, //JSON of YumHubMeal
)