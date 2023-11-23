package com.alterjuice.repository.mappers

import com.alterjuice.database.meals_history.MealsHistoryDB
import com.alterjuice.database.water_balance.WaterBalanceDB
import com.alterjuice.domain.model.MealsHistory
import com.alterjuice.domain.model.common.YumHubMeal
import com.alterjuice.domain.model.water.WaterBalance
import com.google.gson.Gson

fun MealsHistoryDB.toDomain(
    gson: Gson
) = MealsHistory(
    id = id,
    dateTimestampSec = dateTimestampSec,
    mealType = mealType,
    yumHubMeal = gson.fromJson(info, YumHubMeal::class.java),
    servingGram = servingGram
)

fun MealsHistory.toDB(gson: Gson) = MealsHistoryDB(
    id = id,
    dateTimestampSec = dateTimestampSec,
    mealType = mealType,
    info = gson.toJson(yumHubMeal),
    servingGram = servingGram
)

fun WaterBalanceDB.toDomain() = WaterBalance(
    dayTimestampSec = dayTimestampSec,
    balanceML = balanceML
)