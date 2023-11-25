package com.alterjuice.database.water_balance

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.alterjuice.utils.DateTimeUtils
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.mapNotNull


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
    fun getWaterBalanceFlowForDate(dayTimestampSec: Long): Flow<WaterBalanceDB> {
        return internalGetWaterBalanceFlowForDate(DateTimeUtils.getNormalizedStartDate(
            dayTimestampSec
        )).mapNotNull { it.firstOrNull() }
    }


    suspend fun getWaterBalanceForDate(dayTimestampSec: Long): WaterBalanceDB? {
        return internalGetWaterBalanceForDate(DateTimeUtils.getNormalizedStartDate(
            dayTimestampSec
        )).firstOrNull()
    }

    suspend fun updateOrAddWaterBalanceForDate(waterBalance: WaterBalanceDB) {
        val normalizedDate = DateTimeUtils.getNormalizedStartDate(waterBalance.dayTimestampSec)
        return internalUpdateOrAddWaterBalanceForDate(waterBalance.copy(
            dayTimestampSec = normalizedDate
        ))
    }
}