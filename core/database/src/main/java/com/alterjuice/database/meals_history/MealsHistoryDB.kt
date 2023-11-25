package com.alterjuice.database.meals_history

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.alterjuice.domain.model.common.MealType


@Entity(tableName = "MealsHistory")
data class MealsHistoryDB(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo("id") val id: Int,
    @ColumnInfo(name = "date") val dateTimestampSec: Long,
    @ColumnInfo(name="meal_type") val mealType: MealType,
    @ColumnInfo(name="meal_info") val info: String, // JSON of YumHubMeal
)