package com.alterjuice.compose_utils.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.alterjuice.compose_utils.data.model.HistoryDate
import com.alterjuice.resources.R
import com.alterjuice.theming.theme.extension.primaryVariantSchema
import com.alterjuice.utils.DateTimeUtils

@Composable
fun YumHubCalendarBar(
    modifier: Modifier,
    historyDate: HistoryDate,
    contentPaddings: PaddingValues,
    onClick: () -> Unit,
) {
    YumHubOutlinedCard(
        modifier = modifier.fillMaxWidth().clickable(onClick = onClick),
        contentPaddingValues = contentPaddings,
        color = MaterialTheme.primaryVariantSchema.color.copy(alpha = 0.8f),
        surfaceShape = MaterialTheme.shapes.medium.copy(topStart = CornerSize(0f), topEnd = CornerSize(0f))
    ) {
        val dateToShow = remember(historyDate) {
            when (val it = historyDate) {
                HistoryDate.Unspecified -> "-"
                is HistoryDate.Range -> {
                    val fromDate = DateTimeUtils.secToShortDateFormatted(it.fromDayTimestampSec)
                    val toDate = DateTimeUtils.secToShortDateFormatted(it.toDayTimestampSec)
                    "From $fromDate to $toDate"
                }
                is HistoryDate.Single -> {
                    DateTimeUtils.secToLongDateFormatted(it.dayTimestampSec)
                }
            }
        }
        Text(
            modifier = Modifier.align(Alignment.CenterStart),
            text = dateToShow,
            style = MaterialTheme.typography.bodyLarge,
        )
        Icon(
            modifier = Modifier.size(25.dp).align(Alignment.CenterEnd),
            painter = painterResource(R.drawable.ic_calendar),
            contentDescription = null,
        )
    }
}

@Composable
@Preview
private fun YumHubCalendarBarPreview() {
    YumHubCalendarBar(
        modifier = Modifier,
        historyDate = HistoryDate.Single(DateTimeUtils.getNormalizedStartDateTodaySec()),
        contentPaddings = PaddingValues(16.dp),
        onClick = {}
    )
}