package com.alterjuice.domain.model.nutrition

data class NutrientsHistory(
    val dateTimestampSec: Long,
    val proteins: Double = 0.0,
    val energy: Double = 0.0,
    val carbs: Double = 0.0,
    val fat: Double = 0.0,
    val fiber: Double = 0.0,
    val sugars: Double = 0.0,
    val sugarsAdded: Double = 0.0,
    val calcium: Double = 0.0,
)