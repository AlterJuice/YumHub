package com.alterjuice.database.meals_history

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.alterjuice.utils.DateTimeUtils
import kotlinx.coroutines.flow.Flow

@Dao
interface MealsHistoryDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE, entity = MealsHistoryDB::class)
    suspend fun addMealToHistory(mealDB: MealsHistoryDB)

    @Query("SELECT * FROM MealsHistory WHERE date BETWEEN :leftBoundTime AND :rightBoundTime")
    suspend fun getMealsHistoryBetween(
        leftBoundTime: Long,
        rightBoundTime: Long
    ): List<MealsHistoryDB>

    @Query("SELECT * FROM MealsHistory WHERE date BETWEEN :leftBoundTime AND :rightBoundTime")
    fun getMealsHistoryFlowBetween(
        leftBoundTime: Long,
        rightBoundTime: Long
    ): Flow<List<MealsHistoryDB>>

    @Query("DELETE FROM MealsHistory WHERE date = :dateTimestampMs")
    suspend fun deleteMealFromHistoryByID(dateTimestampMs: Long)

    suspend fun getMealsHistoryForDate(timeMs: Long): List<MealsHistoryDB> {
        val startDayAtMs = DateTimeUtils.getNormalizedStartDateFromMsToMs(timeMs).times(1000)
        val endDayAtMs = startDayAtMs + (24 * 60 * 59 * 1000)
        return getMealsHistoryBetween(
            leftBoundTime = startDayAtMs,
            rightBoundTime = endDayAtMs
        )
    }

    fun getMealsHistoryForDateFlow(timeMs: Long): Flow<List<MealsHistoryDB>> {
        val startDayAtMs = DateTimeUtils.getNormalizedStartDateFromMsToMs(timeMs)
        val endDayAtMs = startDayAtMs + (24 * 60 * 59 * 1000)
        return getMealsHistoryFlowBetween(
            leftBoundTime = startDayAtMs,
            rightBoundTime = endDayAtMs
        )
    }

    suspend fun getMealsHistoryForToday(): List<MealsHistoryDB> {
        val startDayAt = DateTimeUtils.getNormalizedStartDateTodayMs()
        return getMealsHistoryForDate(
            timeMs = startDayAt
        )
    }

    fun getMealsHistoryForTodayFlow(): Flow<List<MealsHistoryDB>> {
        val startDayAt = DateTimeUtils.getNormalizedStartDateTodayMs()
        return getMealsHistoryForDateFlow(startDayAt)
    }
}