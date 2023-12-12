package com.alterjuice.compose_utils.data.model

sealed interface HistoryDate {
    data class Single(val dayTimestampSec: Long): HistoryDate
    data class Range(val fromDayTimestampSec: Long, val toDayTimestampSec: Long): HistoryDate
    data object Unspecified: HistoryDate

    fun asSingle() = this as? Single
    fun asRange() = this as? Range
}