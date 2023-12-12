package com.alterjuice.compose_utils.data.model

import com.alterjuice.utils.DateTimeUtils
import com.patrykandpatrick.vico.core.entry.ChartEntryModel

class UserHistoryChartModel(
    val entries: List<ModelEntry>,
    val dates: List<Long>,
    val valueLabelFormatter: (Float, ChartEntryModel) -> String,
    val dateLabelFormatter: (timeMS: Long) -> String,
    val showLegends: Boolean = entries.size > 1
) {
    companion object {
        fun empty() = UserHistoryChartModel(
            entries = emptyList(),
            dates = emptyList(),
            valueLabelFormatter = { value, entry -> value.toString() },
            dateLabelFormatter = { DateTimeUtils.msToDateFormattedWithTime(it) },
        )

    }
}

class ModelEntry(
    val legendLabel: String,
    val values: List<Float>
)
