package com.alterjuice.database.user_measurements

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.alterjuice.domain.model.MeasurementTypes

@Entity(tableName = "UserMeasurements")
data class UserMeasurementsHistoryDB(
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "dayStartTimestampSec") val dayTimestampSec: Long,
    @ColumnInfo(name = "type") val measurementType: MeasurementTypes,
    @ColumnInfo(name = "value") val value: Float,
)