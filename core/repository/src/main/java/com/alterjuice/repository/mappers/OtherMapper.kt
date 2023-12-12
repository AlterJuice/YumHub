package com.alterjuice.repository.mappers

import com.alterjuice.database.meals_history.MealsHistoryDB
import com.alterjuice.database.nutrients_history.NutrientsHistoryDB
import com.alterjuice.database.user_measurements.UserMeasurementsHistoryDB
import com.alterjuice.domain.model.MealsHistory
import com.alterjuice.domain.model.MeasurementTypes
import com.alterjuice.domain.model.common.YumHubMeal
import com.alterjuice.domain.model.nutrition.NutrientsHistory
import com.alterjuice.domain.model.user.UserMeasurements
import com.alterjuice.domain.model.water.WaterBalance
import com.google.gson.Gson



fun UserMeasurementsHistoryDB.toUserHeight() = UserMeasurements.Height(
    dayTimestampSec = dayTimestampSec,
    height = value
)

fun UserMeasurementsHistoryDB.toWaterBalance() = WaterBalance(
    dayTimestampSec = dayTimestampSec,
    balanceML = value.toInt()
)

fun UserMeasurementsHistoryDB.toWaterMeasurement() = UserMeasurements.WaterBalance(
    dayTimestampSec = dayTimestampSec,
    balanceML = value.toInt()
)

fun UserMeasurements.toDB() = UserMeasurementsHistoryDB(
    dayTimestampSec = dayTimestampSec,
    measurementType = type,
    value = getValue()
)

fun UserMeasurementsHistoryDB.toUserWeight() = UserMeasurements.Weight(
    dayTimestampSec = dayTimestampSec,
    weight = value
)

fun UserMeasurementsHistoryDB.toUserMeasurement() = when (this.measurementType) {
    MeasurementTypes.WATER_BALANCE -> toWaterMeasurement()
    MeasurementTypes.HEIGHT -> toUserHeight()
    MeasurementTypes.WEIGHT -> toUserWeight()
}


fun MealsHistoryDB.toDomain(
    gson: Gson
) = MealsHistory(
    dateTimestampMs = dateTimestampMs,
    mealType = mealType,
    yumHubMeal = gson.fromJson(info, YumHubMeal::class.java)
)

fun NutrientsHistoryDB.toDomain() = NutrientsHistory(
    dateTimestampSec = dateTimestampSec,
    proteins = proteins,
    energy = energy,
    carbs = carbs,
    fat = fat,
    fiber = fiber,
    sugars = sugars,
    sugarsAdded = sugarsAdded,
    calcium = calcium,
)

fun MealsHistory.toDB(gson: Gson) = MealsHistoryDB(
    dateTimestampMs = dateTimestampMs,
    mealType = mealType,
    info = gson.toJson(yumHubMeal)
)

