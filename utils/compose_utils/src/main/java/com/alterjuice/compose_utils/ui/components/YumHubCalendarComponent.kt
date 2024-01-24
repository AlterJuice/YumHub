package com.alterjuice.compose_utils.ui.components


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.rememberNestedScrollInteropConnection
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.alterjuice.compose_utils.data.model.HistoryDate
import com.alterjuice.compose_utils.ui.extensions.rememberLambda
import com.alterjuice.utils.DateTimeUtils
import java.time.*
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun YumHubCalendarComponent(
    modifier: Modifier,
    onDismiss: () -> Unit,
    availableDates: List<Long>,
    historyDate: HistoryDate,
    historyDateReceiver: (HistoryDate) -> Unit
) {
    DatePickerDialog(onDismissRequest = { onDismiss() }, confirmButton = { /*TODO*/ }) {
        var isRangeEnabled by remember { mutableStateOf(false) }
        val yearRange = remember {
            // val current = ZonedDateTime.now(ZoneOffset.UTC).year
            IntRange(2021, 2029)
        }

        val datePickerModifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(0.7f)

        //val selectable = remember(availableDates) {
        //    object : SelectableDates {
        //        val least = availableDates.minOrNull() ?: 0L
        //        val max = availableDates.maxOrNull() ?: 0L
        //        override fun isSelectableDate(utcTimeMillis: Long): Boolean {
        //            // if (utcTimeMillis < least) return false
        //            // if (utcTimeMillis > max) return false
        //            // return availableDates.binarySearch(utcTimeMillis) > 0
        //            return true
        //        }
        //        override fun isSelectableYear(year: Int): Boolean {
        //            return year >= 2022
        //        }
        //    }
        //}

        val onDateClick = remember { mutableStateOf<Runnable>(Runnable{})}
        if (isRangeEnabled) {
            val dateRangePickerState = rememberDateRangePickerState(
                initialSelectedStartDateMillis = historyDate.asRange()?.fromDayTimestampSec?.times(1_000L),
                initialSelectedEndDateMillis = historyDate.asRange()?.toDayTimestampSec?.times(1_000L),
                yearRange = yearRange,
                // selectableDates = selectable
            )
            LaunchedEffect(Unit) {
                onDateClick.value = Runnable {
                    val startDate = dateRangePickerState.selectedStartDateMillis?.div(1000L)
                    val endDate = dateRangePickerState.selectedEndDateMillis?.div(1000L)
                    if (startDate == null) {
                        // globalToast(context, "Choose start date")
                        return@Runnable
                    }
                    if (endDate == null) {
                        // globalToast(context, "Choose end date")
                        return@Runnable
                    }
                    historyDateReceiver(HistoryDate.Range(startDate, endDate))
                }
            }
            DateRangePicker(
                state = dateRangePickerState,
                modifier = datePickerModifier,
                title = null,
                headline = null,
                showModeToggle = false,
            )
        } else {
            val datePickerState = rememberDatePickerState(
                initialSelectedDateMillis = historyDate.asSingle()?.dayTimestampSec?.times(1_000L),
                initialDisplayMode = DisplayMode.Picker,
                // selectableDates = selectable,
                 yearRange = yearRange
            )

            LaunchedEffect(Unit) {
                onDateClick.value = Runnable {
                    val selectedDate = datePickerState.selectedDateMillis?.div(1_000L)
                    if (selectedDate == null) {
                        // globalToast(context, "Choose training date")
                        return@Runnable
                    }
                    historyDateReceiver(HistoryDate.Single(selectedDate))
                }
            }
            DatePicker(
                state = datePickerState,
                title = null,
                headline = null,
                modifier = datePickerModifier,
                showModeToggle = false
            )
        }
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {

            Checkbox(
                checked = isRangeEnabled,
                onCheckedChange = rememberLambda { isRangeEnabled = it },
            )

            Text(
                text = "Use range selection",
                style = MaterialTheme.typography.bodyLarge
            )
            IconButton(onClick = rememberLambda {
                onDateClick.value.run()
                onDismiss()
            }) {
                Text(
                    text = "Ok",
                    modifier = Modifier.padding(10.dp),
                    style = MaterialTheme.typography.bodyLarge
                )
            }
        }
    }
}

@Composable
@Preview
private fun YumHubCalendarComponentPreview() {
    YumHubCalendarComponent(
        modifier = Modifier,
        onDismiss = {},
        availableDates = listOf(0),
        historyDate = HistoryDate.Range(
            DateTimeUtils.getNormalizedStartDateTodaySec(),
            DateTimeUtils.getNormalizedStartDateTodaySec() + 10 * 24 * 60 * 60
        ),
        historyDateReceiver = {}
    )
}