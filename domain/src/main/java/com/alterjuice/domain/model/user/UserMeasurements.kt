package com.alterjuice.domain.model.user

import com.alterjuice.domain.model.MeasurementTypes

sealed interface UserMeasurements {
    val type: MeasurementTypes
    val dayTimestampSec: Long
    fun getValue(): Float

    data class Height(
        override val dayTimestampSec: Long,
        val height: Float
    ): UserMeasurements {
        override val type = MeasurementTypes.HEIGHT
        override fun getValue(): Float = height
    }

    data class Weight(
        override val dayTimestampSec: Long,
        val weight: Float
    ): UserMeasurements {
        override val type = MeasurementTypes.WEIGHT
        override fun getValue(): Float = weight
    }

    data class WaterBalance(
        override val dayTimestampSec: Long,
        val balanceML: Int
    ): UserMeasurements {
        override val type = MeasurementTypes.WATER_BALANCE
        override fun getValue(): Float = balanceML.toFloat()
    }

    fun asHeight() = this as? Height
    fun asWeight() = this as? Weight
    fun asWaterBalance() = this as? WaterBalance
}