package com.alterjuice.database.category_history

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.alterjuice.domain.model.common.MealCategories

@Dao
interface CategoryEatenHistoryDao {

    // Will be only one or zero items in list
    @Query("SELECT ate_count FROM CategoryEatenHistory WHERE meal_category = :categoryName LIMIT 1")
    suspend fun getCategoryAteCount(categoryName: MealCategories): List<Int>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCategoryAteCount(categoryEatenHistoryDB: CategoryEatenHistoryDB)

    suspend fun increaseCategoryAteCount(mealCategory: MealCategories) {
        val old = getCategoryAteCount(mealCategory).firstOrNull()?: 0
        val new = CategoryEatenHistoryDB(
            mealCategory = mealCategory,
            categoryAteCount = (old + 1).coerceAtLeast(0)
        )
        insertCategoryAteCount(new)
    }

    suspend fun decreaseCategoryAteCount(mealCategory: MealCategories) {
        val old = getCategoryAteCount(mealCategory).firstOrNull()?: 0
        val new = CategoryEatenHistoryDB(
            mealCategory = mealCategory,
            categoryAteCount = (old - 1).coerceAtLeast(0)
        )
        insertCategoryAteCount(new)
    }

}