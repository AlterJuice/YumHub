package com.alterjuice.database.water_balance

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "WaterBalance")
data class WaterBalanceDB(
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "dayStartTimestampSec") val dayTimestampSec: Long,

    @ColumnInfo(name="balance") val balanceML: Int,
)