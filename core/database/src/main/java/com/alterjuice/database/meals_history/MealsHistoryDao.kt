package com.alterjuice.database.meals_history

import androidx.room.Dao
import androidx.room.Query
import com.alterjuice.utils.DateTimeUtils
import kotlinx.coroutines.flow.Flow

@Dao
interface MealsHistoryDao {

    @Query("SELECT * FROM MealsHistory WHERE date BETWEEN :leftBoundTime AND :rightBoundTime")
    suspend fun getMealsHistoryBetween(leftBoundTime: Long, rightBoundTime: Long): List<MealsHistoryDB>

    @Query("SELECT * FROM MealsHistory WHERE date BETWEEN :leftBoundTime AND :rightBoundTime")
    fun getMealsHistoryFlowBetween(leftBoundTime: Long, rightBoundTime: Long): Flow<List<MealsHistoryDB>>

    @Query("DELETE FROM MealsHistory WHERE id = :mealID")
    suspend fun deleteMealFromHistoryByID(mealID: Int)

    suspend fun getMealsHistoryForDate(dayTimestampSec: Long): List<MealsHistoryDB> {
        val startDayAt = DateTimeUtils.getNormalizedStartDate(dayTimestampSec)
        val endDayAt = startDayAt + (24 * 60 * 60)
        return getMealsHistoryBetween(
            leftBoundTime = startDayAt,
            rightBoundTime = endDayAt
        )
    }

    suspend fun getMealsHistoryForToday(): List<MealsHistoryDB> {
        val startDayAt = DateTimeUtils.getNormalizedStartDateToday()
        return getMealsHistoryForDate(startDayAt)
    }

    fun getMealsHistoryForDateFlow(dayTimestampSec: Long): Flow<List<MealsHistoryDB>> {
        val startDayAt = DateTimeUtils.getNormalizedStartDate(dayTimestampSec)
        val endDayAt = startDayAt + (24 * 60 * 60)
        return getMealsHistoryFlowBetween(
            leftBoundTime = startDayAt,
            rightBoundTime = endDayAt
        )
    }

    fun getMealsHistoryForTodayFlow(): Flow<List<MealsHistoryDB>> {
        val startDayAt = DateTimeUtils.getNormalizedStartDateToday()
        return getMealsHistoryForDateFlow(startDayAt)
    }
}