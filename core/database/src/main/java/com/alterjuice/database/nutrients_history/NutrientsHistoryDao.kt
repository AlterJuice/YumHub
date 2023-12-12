package com.alterjuice.database.nutrients_history

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.alterjuice.domain.model.common.YumHubMeal
import com.alterjuice.domain.model.nutrition.NutritionEnum
import kotlinx.coroutines.flow.Flow


@Dao
interface NutrientsHistoryDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE, entity = NutrientsHistoryDB::class)
    suspend fun insertNutrientHistory(nutrientsHistoryDB: NutrientsHistoryDB)

    // Will be only one or zero items in list
    @Query("SELECT * FROM NutrientsHistory WHERE date = :dayTimestampSec LIMIT 1")
    suspend fun getNutrientsHistoryForDate(dayTimestampSec: Long): List<NutrientsHistoryDB>

    // Will be only one or zero items in list
    @Query("SELECT * FROM NutrientsHistory WHERE date = :dayTimestampSec LIMIT 1")
    fun getNutrientsHistoryFlowForDate(dayTimestampSec: Long): Flow<List<NutrientsHistoryDB>>

    @Query("SELECT * FROM NutrientsHistory WHERE date BETWEEN :fromDayTimestampSec AND :toDayTimestampSec")
    suspend fun getNutrientsHistoryForDates(
        fromDayTimestampSec: Long,
        toDayTimestampSec: Long
    ): List<NutrientsHistoryDB>

    @Query("SELECT * FROM NutrientsHistory WHERE date BETWEEN :fromDayTimestampSec AND :toDayTimestampSec")
    fun getNutrientsHistoryFlowForDates(
        fromDayTimestampSec: Long,
        toDayTimestampSec: Long
    ): Flow<List<NutrientsHistoryDB>>


    suspend fun updateNutrientsHistoryByAddingMealForDate(meal: YumHubMeal, dayTimestampSec: Long) {
        val old = getNutrientsHistoryForDate(dayTimestampSec).firstOrNull()
        val updated = if (old == null) {
            // we should just create empty object to add it to history. This won't be even
            // happened, but the logic requires
            NutrientsHistoryDB(dayTimestampSec)
        } else {
            old.copy(
                proteins = old.proteins + meal.withServings(1.0, NutritionEnum.Protein),
                energy = old.energy + meal.withServings(1.0, NutritionEnum.EnergyCalories),
                carbs = old.carbs + meal.withServings(1.0, NutritionEnum.Carbs),
                fat = old.fat + meal.withServings(1.0, NutritionEnum.Fat),
                fiber = old.fiber + meal.withServings(1.0, NutritionEnum.Fiber),
                sugars = old.sugars + meal.withServings(1.0, NutritionEnum.Sugars),
                sugarsAdded = old.sugarsAdded + meal.withServings(1.0, NutritionEnum.SugarsAdded)
            )
        }
        insertNutrientHistory(updated)
    }

    suspend fun updateNutrientsHistoryByRemovingMealForDate(
        meal: YumHubMeal,
        dayTimestampSec: Long
    ) {
        val old = getNutrientsHistoryForDate(dayTimestampSec).firstOrNull()
        val updated = if (old == null) {
            // we should just create empty object to add it to history. This won't be even
            // happened, but the logic requires
            NutrientsHistoryDB(dayTimestampSec)
        } else {
            old.copy(
                proteins = old.proteins - meal.withServings(1.0, NutritionEnum.Protein),
                energy = old.energy - meal.withServings(1.0, NutritionEnum.EnergyCalories),
                carbs = old.carbs - meal.withServings(1.0, NutritionEnum.Carbs),
                fat = old.fat - meal.withServings(1.0, NutritionEnum.Fat),
                fiber = old.fiber - meal.withServings(1.0, NutritionEnum.Fiber),
                sugars = old.sugars - meal.withServings(1.0, NutritionEnum.Sugars),
                sugarsAdded = old.sugarsAdded - meal.withServings(1.0, NutritionEnum.SugarsAdded)
            )
        }
        insertNutrientHistory(updated)
    }
}