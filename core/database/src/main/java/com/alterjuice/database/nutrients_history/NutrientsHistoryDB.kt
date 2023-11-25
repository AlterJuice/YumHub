package com.alterjuice.database.nutrients_history

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "NutrientsHistory")
data class NutrientsHistoryDB(
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "date") val dateTimestampSec: Long,
    @ColumnInfo(name = "proteins", defaultValue = "0.0") val proteins: Double = 0.0,
    @ColumnInfo(name = "energy", defaultValue = "0.0") val energy: Double = 0.0,
    @ColumnInfo(name = "carbs", defaultValue = "0.0") val carbs: Double = 0.0,
    @ColumnInfo(name = "fat", defaultValue = "0.0") val fat: Double = 0.0,
    @ColumnInfo(name = "fiber", defaultValue = "0.0") val fiber: Double = 0.0,
    @ColumnInfo(name = "sugars", defaultValue = "0.0") val sugars: Double = 0.0,
    @ColumnInfo(name = "sugarsAdded", defaultValue = "0.0") val sugarsAdded: Double = 0.0,
    @ColumnInfo(name = "calcium", defaultValue = "0.0") val calcium: Double = 0.0,

)