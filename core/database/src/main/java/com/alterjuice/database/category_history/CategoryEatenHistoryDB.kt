package com.alterjuice.database.category_history

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.alterjuice.domain.model.common.MealCategories


@Entity(tableName = "CategoryEatenHistory")
data class CategoryEatenHistoryDB(
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo("meal_category") val mealCategory: MealCategories,
    @ColumnInfo("ate_count", defaultValue = "0") val categoryAteCount: Int,
)