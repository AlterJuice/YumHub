package com.alterjuice.repository.repository

import com.alterjuice.database.category_history.CategoryEatenHistoryDao
import com.alterjuice.database.nutrients_history.NutrientsHistoryDao
import com.alterjuice.domain.model.common.YumHubMeal
import com.alterjuice.domain.model.nutrition.NutrientsHistory
import com.alterjuice.domain.repository.NutrientsHistoryRepository
import com.alterjuice.repository.mappers.toDomain
import com.alterjuice.utils.DateTimeUtils
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.mapNotNull

class NutrientsHistoryRepositoryImpl(
    private val nutrientsHistoryDao: NutrientsHistoryDao,
    private val categoryEatenHistoryDao: CategoryEatenHistoryDao,
): NutrientsHistoryRepository {
    override fun getNutrientsHistoryFlowForDate(dayTimestampSec: Long): Flow<NutrientsHistory> {
        val normalizedDate = DateTimeUtils.getNormalizedStartDateFromSec(dayTimestampSec)
        return nutrientsHistoryDao.getNutrientsHistoryFlowForDate(normalizedDate).mapNotNull {
            it.firstOrNull()?.toDomain()
        }
    }

    override suspend fun getNutrientsHistoryForDate(dayTimestampSec: Long): NutrientsHistory? {
        val normalizedDate = DateTimeUtils.getNormalizedStartDateFromSec(dayTimestampSec)
        return nutrientsHistoryDao.getNutrientsHistoryForDate(normalizedDate).firstOrNull()?.toDomain()
    }

    override suspend fun getNutrientsHistoryForDates(
        fromDayTimestampSec: Long,
        toDayTimestampSec: Long
    ): List<NutrientsHistory> {
        return nutrientsHistoryDao.getNutrientsHistoryForDates(
            fromDayTimestampSec = fromDayTimestampSec,
            toDayTimestampSec = toDayTimestampSec,
        ).map {
            it.toDomain()
        }
    }

    override fun getNutrientsHistoryFlowForDates(
        fromDayTimestampSec: Long,
        toDayTimestampSec: Long
    ): Flow<List<NutrientsHistory>> {
        return nutrientsHistoryDao.getNutrientsHistoryFlowForDates(
            fromDayTimestampSec = fromDayTimestampSec,
            toDayTimestampSec = toDayTimestampSec,
        ).map { it.map { it.toDomain() } }
    }

    override suspend fun updateNutrientsHistoryByAddingMealForDate(
        meal: YumHubMeal,
        dayTimestampSec: Long
    ) {
        val normalizedDate = DateTimeUtils.getNormalizedStartDateFromSec(dayTimestampSec)
        meal.categoriesTags.forEach {
            categoryEatenHistoryDao.increaseCategoryAteCount(it)
        }
        nutrientsHistoryDao.updateNutrientsHistoryByAddingMealForDate(meal, normalizedDate)
    }

    override suspend fun updateNutrientsHistoryByRemovingMealForDate(
        meal: YumHubMeal,
        dayTimestampSec: Long
    ) {
        val normalizedDate = DateTimeUtils.getNormalizedStartDateFromSec(dayTimestampSec)
        meal.categoriesTags.forEach {
            categoryEatenHistoryDao.decreaseCategoryAteCount(it)
        }
        nutrientsHistoryDao.updateNutrientsHistoryByRemovingMealForDate(meal, normalizedDate)
    }

}