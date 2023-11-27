package com.alterjuice.database.user_measurements

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.alterjuice.domain.model.MeasurementTypes
import com.alterjuice.utils.DateTimeUtils
import kotlinx.coroutines.flow.Flow


@Dao
interface InternalUserMeasurementsHistoryDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun internalUpdateOrAddMeasurementsForDate(entity: UserMeasurementsHistoryDB)


    @Query("SELECT * FROM UserMeasurements WHERE dayStartTimestampSec = :dayTimestampSec AND type IN (:types)")
    suspend fun internalGetMeasurementsForDate(
        dayTimestampSec: Long,
        types: List<MeasurementTypes>
    ): List<UserMeasurementsHistoryDB>


    @Query("SELECT * FROM UserMeasurements WHERE type in (:types) AND dayStartTimestampSec BETWEEN :fromDayTimestampSec AND :toDayTimestampSec")
    suspend fun internalGetMeasurementsForDates(
        fromDayTimestampSec: Long,
        toDayTimestampSec: Long,
        types: List<MeasurementTypes>
    ): List<UserMeasurementsHistoryDB>

    @Query("SELECT * FROM UserMeasurements WHERE type IN (:types)")
    suspend fun getAllMeasurementsByType(types: List<MeasurementTypes>): List<UserMeasurementsHistoryDB>


    @Query("SELECT * FROM UserMeasurements WHERE dayStartTimestampSec = :dayTimestampSec AND type IN (:types)")
    fun internalGetMeasurementsForDateFlow(
        dayTimestampSec: Long,
        types: List<MeasurementTypes>
    ): Flow<List<UserMeasurementsHistoryDB>>


    @Query("SELECT * FROM UserMeasurements WHERE type in (:types) AND dayStartTimestampSec BETWEEN :fromDayTimestampSec AND :toDayTimestampSec")
    fun internalGetMeasurementsForDatesFlow(
        fromDayTimestampSec: Long,
        toDayTimestampSec: Long,
        types: List<MeasurementTypes>
    ): Flow<List<UserMeasurementsHistoryDB>>

    @Query("SELECT * FROM UserMeasurements WHERE type IN (:types)")
    fun getAllMeasurementsByTypeFlow(types: List<MeasurementTypes>): Flow<List<UserMeasurementsHistoryDB>>

}


@Dao
interface UserMeasurementsHistoryDao : InternalUserMeasurementsHistoryDao {

    suspend fun getMeasurementsForDate(
        dayTimestampSec: Long,
        types: List<MeasurementTypes>
    ): List<UserMeasurementsHistoryDB> {
        return internalGetMeasurementsForDate(
            dayTimestampSec = DateTimeUtils.getNormalizedStartDateFromSec(dayTimestampSec),
            types = types
        )
    }


    suspend fun getMeasurementsForDates(
        fromDayTimestampSec: Long,
        toDayTimestampSec: Long,
        types: List<MeasurementTypes>
    ): List<UserMeasurementsHistoryDB> {
        val normalizedFrom = DateTimeUtils.getNormalizedStartDateFromSec(fromDayTimestampSec)
        val normalizedTo = DateTimeUtils.getNormalizedStartDateFromSec(toDayTimestampSec)
        return internalGetMeasurementsForDates(
            fromDayTimestampSec = normalizedFrom,
            toDayTimestampSec = normalizedTo,
            types = types
        )
    }


    fun getMeasurementsForDateFlow(
        dayTimestampSec: Long,
        types: List<MeasurementTypes>
    ): Flow<List<UserMeasurementsHistoryDB>> {
        return internalGetMeasurementsForDateFlow(
            dayTimestampSec = DateTimeUtils.getNormalizedStartDateFromSec(dayTimestampSec),
            types = types
        )
    }


    fun getMeasurementsForDatesFlow(
        fromDayTimestampSec: Long,
        toDayTimestampSec: Long,
        types: List<MeasurementTypes>
    ): Flow<List<UserMeasurementsHistoryDB>> {
        val normalizedFrom = DateTimeUtils.getNormalizedStartDateFromSec(fromDayTimestampSec)
        val normalizedTo = DateTimeUtils.getNormalizedStartDateFromSec(toDayTimestampSec)
        return internalGetMeasurementsForDatesFlow(
            fromDayTimestampSec = normalizedFrom,
            toDayTimestampSec = normalizedTo,
            types = types
        )
    }
}