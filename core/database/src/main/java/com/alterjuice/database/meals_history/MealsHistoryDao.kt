package com.alterjuice.database.meals_history

import androidx.room.Dao
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import java.time.Instant
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.ZoneId

fun defaultZoneIDOffset() = ZoneId.systemDefault().rules.getOffset(Instant.now())

@Dao
interface MealsHistoryDao {

    @Query("SELECT * FROM MealsHistory WHERE date BETWEEN :leftBoundTime AND :rightBoundTime")
    suspend fun getMealsHistoryBetween(leftBoundTime: Long, rightBoundTime: Long): List<MealsHistoryDB>

    @Query("SELECT * FROM MealsHistory WHERE date BETWEEN :leftBoundTime AND :rightBoundTime")
    fun getMealsHistoryFlowBetween(leftBoundTime: Long, rightBoundTime: Long): Flow<List<MealsHistoryDB>>

    @Query("DELETE FROM MealsHistory WHERE id = :mealID")
    suspend fun deleteMealFromHistoryByID(mealID: Int)

    suspend fun getMealsHistoryForDay(dayTimestampSec: Long): List<MealsHistoryDB> {
        ZoneId.systemDefault().rules.getOffset(Instant.now())
        val date = LocalDateTime
            .ofEpochSecond(dayTimestampSec, 0, defaultZoneIDOffset())
            .toLocalDate()
        val startDayAt = date.atStartOfDay().toEpochSecond(defaultZoneIDOffset())
        val endDayAt = startDayAt + (24 * 60 * 60)
        return getMealsHistoryBetween(
            leftBoundTime = startDayAt,
            rightBoundTime = endDayAt
        )
    }

    suspend fun getMealsHistoryForToday(): List<MealsHistoryDB> {
        val startDayAt = LocalDate.now().atStartOfDay(ZoneId.systemDefault()).toEpochSecond()
        return getMealsHistoryForDay(startDayAt)
    }

    fun getMealsHistoryForDayFlow(dayTimestampSec: Long): Flow<List<MealsHistoryDB>> {
        val date = LocalDateTime
            .ofEpochSecond(dayTimestampSec, 0, defaultZoneIDOffset())
            .toLocalDate()
        val startDayAt = date.atStartOfDay().toEpochSecond(defaultZoneIDOffset())
        val endDayAt = startDayAt + (24 * 60 * 60)
        return getMealsHistoryFlowBetween(
            leftBoundTime = startDayAt,
            rightBoundTime = endDayAt
        )
    }

    fun getMealsHistoryForTodayFlow(): Flow<List<MealsHistoryDB>> {
        val startDayAt = LocalDate.now().atStartOfDay(ZoneId.systemDefault()).toEpochSecond()
        return getMealsHistoryForDayFlow(startDayAt)
    }
}