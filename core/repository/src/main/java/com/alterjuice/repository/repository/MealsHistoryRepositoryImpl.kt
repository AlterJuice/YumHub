package com.alterjuice.repository.repository

import com.alterjuice.database.meals_history.MealsHistoryDB
import com.alterjuice.database.meals_history.MealsHistoryDao
import com.alterjuice.database.user_measurements.UserMeasurementsHistoryDB
import com.alterjuice.database.user_measurements.UserMeasurementsHistoryDao
import com.alterjuice.domain.model.MealsHistory
import com.alterjuice.domain.model.MeasurementTypes
import com.alterjuice.domain.model.common.MealType
import com.alterjuice.domain.model.common.YumHubMeal
import com.alterjuice.domain.model.water.WaterBalance
import com.alterjuice.domain.repository.MealsHistoryRepository
import com.alterjuice.domain.repository.NutrientsHistoryRepository
import com.alterjuice.repository.mappers.toDomain
import com.alterjuice.repository.mappers.toWaterBalance
import com.alterjuice.utils.DateTimeUtils
import com.google.gson.Gson
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class MealsHistoryRepositoryImpl(
    private val mealsHistoryDao: MealsHistoryDao,
    private val nutrientsHistoryRepository: NutrientsHistoryRepository,
    private val measurementsHistoryDao: UserMeasurementsHistoryDao,
    private val gson: Gson,
): MealsHistoryRepository {
    override suspend fun getAllMeals(): List<MealsHistory> {
        return mealsHistoryDao.getAllMeals().map {
            it.toDomain(gson)
        }
    }

    override suspend fun addMealInfo(meal: YumHubMeal, mealType: MealType) {
        val startOfDayDateSec = DateTimeUtils.getNormalizedStartDateTodaySec()
        val dateTimestampMs = DateTimeUtils.getCurrentTimeMillis()
        nutrientsHistoryRepository.updateNutrientsHistoryByAddingMealForDate(
            meal, startOfDayDateSec
        )
        mealsHistoryDao.addMealToHistory(MealsHistoryDB(
            dateTimestampMs = dateTimestampMs,
            mealType = mealType,
            info = gson.toJson(meal)
        ))
        println(1)
    }

    override suspend fun removeMealInfo(meal: MealsHistory) {
        val startOfDaySec = DateTimeUtils.getNormalizedStartDateFromMsToSec(
            meal.dateTimestampMs
        )
        nutrientsHistoryRepository.updateNutrientsHistoryByRemovingMealForDate(
            meal.yumHubMeal, startOfDaySec
        )
        mealsHistoryDao.deleteMealFromHistoryByID(meal.dateTimestampMs)
    }

    override suspend fun getMealsHistoryToday(): List<MealsHistory> {
        return mealsHistoryDao.getMealsHistoryForToday().map { it.toDomain(gson) }
    }

    override fun getMealsHistoryTodayFlow(): Flow<List<MealsHistory>> {
        return mealsHistoryDao.getMealsHistoryForTodayFlow().map { it.map { it.toDomain(gson) } }
    }

    override suspend fun getMealHistoryForDate(timeMs: Long): List<MealsHistory> {
        return mealsHistoryDao.getMealsHistoryForDate(timeMs = timeMs).map {
            it.toDomain(gson)
        }
    }

    override fun getMealHistoryForDateFlow(timeMs: Long): Flow<List<MealsHistory>> {
        return mealsHistoryDao.getMealsHistoryForDateFlow(timeMs = timeMs).map {
            it.map { it.toDomain(gson) }
        }
    }

}
