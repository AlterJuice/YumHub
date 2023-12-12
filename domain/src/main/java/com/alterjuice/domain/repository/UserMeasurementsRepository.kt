package com.alterjuice.domain.repository

import com.alterjuice.domain.model.MeasurementTypes
import com.alterjuice.domain.model.user.UserMeasurements
import kotlinx.coroutines.flow.Flow

interface UserMeasurementsRepository {

    suspend fun addMeasurement(measurement: UserMeasurements)

    suspend fun getMeasurementsForDate(
        dayTimestampSec: Long,
        types: List<MeasurementTypes> = MeasurementTypes.values().toList()
    ): List<UserMeasurements>

    suspend fun getMeasurementsForDates(
        fromDayTimestampSec: Long,
        toDayTimestampSec: Long,
        types: List<MeasurementTypes> = MeasurementTypes.values().toList()
    ): List<UserMeasurements>

    suspend fun getAllMeasurementsByType(
        types: List<MeasurementTypes> = MeasurementTypes.values().toList()
    ): List<UserMeasurements>

    fun getMeasurementsForDateFlow(
        dayTimestampSec: Long,
        types: List<MeasurementTypes> = MeasurementTypes.values().toList()
    ): Flow<List<UserMeasurements>>

    fun getMeasurementsForDatesFlow(
        fromDayTimestampSec: Long,
        toDayTimestampSec: Long,
        types: List<MeasurementTypes> = MeasurementTypes.values().toList()
    ): Flow<List<UserMeasurements>>

    fun getAllMeasurementsByTypeFlow(
        types: List<MeasurementTypes> = MeasurementTypes.values().toList()
    ): Flow<List<UserMeasurements>>
}