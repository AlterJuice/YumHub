package com.alterjuice.database.nutrients_history

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.alterjuice.domain.model.common.YumHubMeal
import com.alterjuice.domain.model.nutrition.valueOrZero
import kotlinx.coroutines.flow.Flow


@Dao
interface NutrientsHistoryDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNutrientHistory(nutrientsHistoryDB: NutrientsHistoryDB)

    // Will be only one or zero items in list
    @Query("SELECT * FROM NutrientsHistory WHERE date = :dayTimestampSec LIMIT 1")
    suspend fun getNutrientsHistoryForDate(dayTimestampSec: Long): List<NutrientsHistoryDB>

    // Will be only one or zero items in list
    @Query("SELECT * FROM NutrientsHistory WHERE date = :dayTimestampSec LIMIT 1")
    fun getNutrientsHistoryFlowForDate(dayTimestampSec: Long): Flow<List<NutrientsHistoryDB>>


    suspend fun updateNutrientsHistoryByAddingMealForDate(meal: YumHubMeal, dayTimestampSec: Long) {
        val old = getNutrientsHistoryForDate(dayTimestampSec).firstOrNull()
        val updated = if (old == null) {
            // we should just create empty object to add it to history. This won't be even
            // happened, but the logic requires
            NutrientsHistoryDB(dayTimestampSec)
        } else {
            old.copy(
                proteins = old.proteins + meal.protein.valueOrZero,
                energy = old.energy + meal.energyCalories.valueOrZero,
                carbs = old.carbs + meal.carbs.valueOrZero,
                fat = old.fat + meal.fat.valueOrZero,
                fiber = old.fiber + meal.fiber.valueOrZero,
                sugars = old.sugars + meal.sugars.valueOrZero,
                sugarsAdded = old.sugarsAdded + meal.sugarsAdded.valueOrZero
            )
        }
        insertNutrientHistory(updated)
    }

    suspend fun updateNutrientsHistoryByRemovingMealForDate(meal: YumHubMeal, dayTimestampSec: Long) {
        val old = getNutrientsHistoryForDate(dayTimestampSec).firstOrNull()
        val updated = if (old == null) {
            // we should just create empty object to add it to history. This won't be even
            // happened, but the logic requires
            NutrientsHistoryDB(dayTimestampSec)
        } else {
            old.copy(
                proteins = old.proteins - meal.protein.valueOrZero,
                energy = old.energy - meal.energyCalories.valueOrZero,
                carbs = old.carbs - meal.carbs.valueOrZero,
                fat = old.fat - meal.fat.valueOrZero,
                fiber = old.fiber - meal.fiber.valueOrZero,
                sugars = old.sugars - meal.sugars.valueOrZero,
                sugarsAdded = old.sugarsAdded - meal.sugarsAdded.valueOrZero
            )
        }
        insertNutrientHistory(updated)
    }
}