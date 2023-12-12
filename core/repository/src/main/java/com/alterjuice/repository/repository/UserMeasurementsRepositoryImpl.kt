package com.alterjuice.repository.repository

import com.alterjuice.database.user_measurements.UserMeasurementsHistoryDao
import com.alterjuice.domain.model.MeasurementTypes
import com.alterjuice.domain.model.user.UserMeasurements
import com.alterjuice.domain.repository.UserMeasurementsRepository
import com.alterjuice.repository.mappers.toDB
import com.alterjuice.repository.mappers.toUserMeasurement
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class UserMeasurementsRepositoryImpl(
    private val userMeasurementsHistoryDao: UserMeasurementsHistoryDao
) : UserMeasurementsRepository {

    /*
    * userMeasurementsHistoryDao.internalUpdateOrAddMeasurementsForDate(UserMeasurementsHistoryDB(
    dayTimestampSec = DateTimeUtils.getNormalizedStartDateTodaySec() - 10*24*60*60,
    measurementType = MeasurementTypes.WEIGHT,
    value = 90f
))
    * */


    override suspend fun addMeasurement(measurement: UserMeasurements) {
        userMeasurementsHistoryDao.internalUpdateOrAddMeasurementsForDate(
            entity = measurement.toDB()
        )
    }

    override suspend fun getMeasurementsForDate(
        dayTimestampSec: Long,
        types: List<MeasurementTypes>
    ): List<UserMeasurements> {
        return userMeasurementsHistoryDao.getMeasurementsForDate(
            dayTimestampSec = dayTimestampSec,
            types = types
        ).map { it.toUserMeasurement() }
    }

    override suspend fun getMeasurementsForDates(
        fromDayTimestampSec: Long,
        toDayTimestampSec: Long,
        types: List<MeasurementTypes>
    ): List<UserMeasurements> {
        return userMeasurementsHistoryDao.getMeasurementsForDates(
            fromDayTimestampSec = fromDayTimestampSec,
            toDayTimestampSec = toDayTimestampSec,
            types = types
        ).map { it.toUserMeasurement() }
    }

    override suspend fun getAllMeasurementsByType(types: List<MeasurementTypes>): List<UserMeasurements> {
        return userMeasurementsHistoryDao.getAllMeasurementsByType(types).map {
            it.toUserMeasurement()
        }
    }

    override fun getMeasurementsForDateFlow(
        dayTimestampSec: Long,
        types: List<MeasurementTypes>
    ): Flow<List<UserMeasurements>> {
        return userMeasurementsHistoryDao.getMeasurementsForDateFlow(
            dayTimestampSec = dayTimestampSec,
            types = types
        ).map { it.map { it.toUserMeasurement() } }
    }

    override fun getMeasurementsForDatesFlow(
        fromDayTimestampSec: Long,
        toDayTimestampSec: Long,
        types: List<MeasurementTypes>
    ): Flow<List<UserMeasurements>> {
        return userMeasurementsHistoryDao.getMeasurementsForDatesFlow(
            fromDayTimestampSec = fromDayTimestampSec,
            toDayTimestampSec = toDayTimestampSec,
            types = types
        ).map { it.map { it.toUserMeasurement() } }
    }

    override fun getAllMeasurementsByTypeFlow(types: List<MeasurementTypes>): Flow<List<UserMeasurements>> {
        return userMeasurementsHistoryDao.getAllMeasurementsByTypeFlow(types).map {
            it.map { it.toUserMeasurement() }
        }
    }
}