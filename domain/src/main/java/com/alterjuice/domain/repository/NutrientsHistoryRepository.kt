package com.alterjuice.domain.repository

import com.alterjuice.domain.model.common.YumHubMeal
import com.alterjuice.domain.model.nutrition.NutrientsHistory
import kotlinx.coroutines.flow.Flow

interface NutrientsHistoryRepository {
    fun getNutrientsHistoryFlowForDate(dayTimestampSec: Long): Flow<NutrientsHistory>
    suspend fun getNutrientsHistoryForDate(dayTimestampSec: Long): NutrientsHistory?
    suspend fun getNutrientsHistoryForDates(
        fromDayTimestampSec: Long,
        toDayTimestampSec: Long
    ): List<NutrientsHistory>

    fun getNutrientsHistoryFlowForDates(
        fromDayTimestampSec: Long,
        toDayTimestampSec: Long
    ): Flow<List<NutrientsHistory>>

    suspend fun updateNutrientsHistoryByAddingMealForDate(meal: YumHubMeal, dayTimestampSec: Long)
    suspend fun updateNutrientsHistoryByRemovingMealForDate(meal: YumHubMeal, dayTimestampSec: Long)
}
