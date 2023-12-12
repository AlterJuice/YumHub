package com.alterjuice.domain.repository

import com.alterjuice.domain.model.MealsHistory
import com.alterjuice.domain.model.common.MealType
import com.alterjuice.domain.model.common.YumHubMeal
import com.alterjuice.domain.model.nutrition.NutrientsHistory
import com.alterjuice.domain.model.water.WaterBalance
import kotlinx.coroutines.flow.Flow




interface MealsHistoryRepository {
    suspend fun getAllMeals(): List<MealsHistory>

    suspend fun addMealInfo(meal: YumHubMeal, mealType: MealType)
    suspend fun removeMealInfo(meal: MealsHistory)

    suspend fun getMealsHistoryToday(): List<MealsHistory>
    fun getMealsHistoryTodayFlow(): Flow<List<MealsHistory>>


    suspend fun getMealHistoryForDate(timeMs: Long): List<MealsHistory>
    fun getMealHistoryForDateFlow(timeMs: Long): Flow<List<MealsHistory>>


}