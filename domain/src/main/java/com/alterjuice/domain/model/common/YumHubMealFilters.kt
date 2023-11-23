package com.alterjuice.domain.model.common


fun interface YumHubMealFilters {
    fun check(dish: YumHubMeal): Boolean
}