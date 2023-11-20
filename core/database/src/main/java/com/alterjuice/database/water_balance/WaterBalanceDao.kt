package com.alterjuice.database.water_balance

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.alterjuice.database.meals_history.defaultZoneIDOffset
import kotlinx.coroutines.flow.Flow
import java.time.LocalDateTime

fun getNormalizedStartDate(timeSec: Long): Long {
    return LocalDateTime
        .ofEpochSecond(timeSec, 0, defaultZoneIDOffset())
        .toLocalDate()
        .atStartOfDay()
        .toEpochSecond(defaultZoneIDOffset())
}

@Dao
interface InternalWaterBalanceDao {
    @Query("SELECT * FROM WaterBalance WHERE dayStartTimestampSec = :dayTimestampSec LIMIT 1")
    fun internalGetWaterBalanceFlowForDate(dayTimestampSec: Long): Flow<List<WaterBalanceDB>>

    @Query("SELECT * FROM WaterBalance WHERE dayStartTimestampSec = :dayTimestampSec LIMIT 1")
    suspend fun internalGetWaterBalanceForDate(dayTimestampSec: Long): List<WaterBalanceDB>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun internalUpdateOrAddWaterBalanceForDate(waterBalanceDB: WaterBalanceDB)
}


@Dao
interface WaterBalanceDao: InternalWaterBalanceDao {

    // Will be only one or zero items in list
    fun getWaterBalanceFlowForDate(dayTimestampSec: Long): Flow<List<WaterBalanceDB>> {
        return internalGetWaterBalanceFlowForDate(getNormalizedStartDate(
            dayTimestampSec
        ))
    }


    suspend fun getWaterBalanceForDate(dayTimestampSec: Long): List<WaterBalanceDB> {
        return internalGetWaterBalanceForDate(getNormalizedStartDate(
            dayTimestampSec
        ))
    }

    suspend fun updateOrAddWaterBalanceForDate(waterBalance: WaterBalanceDB) {
        val normalizedDate = getNormalizedStartDate(waterBalance.dayTimestampSec)
        return internalUpdateOrAddWaterBalanceForDate(waterBalance.copy(
            dayTimestampSec = normalizedDate
        ))
    }
}