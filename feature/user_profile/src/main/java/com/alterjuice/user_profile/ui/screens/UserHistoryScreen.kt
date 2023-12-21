package com.alterjuice.user_profile.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.alterjuice.compose_utils.ui.components.WaterBalanceWithLeadingIconComponent
import com.alterjuice.compose_utils.ui.components.YumHubCalendarBar
import com.alterjuice.compose_utils.ui.components.YumHubCalendarComponent
import com.alterjuice.compose_utils.ui.components.YumHubOutlinedCard
import com.alterjuice.compose_utils.ui.components.charts.UserHistoryVicoChartWithTitle
import com.alterjuice.compose_utils.ui.extensions.rememberLambda
import com.alterjuice.theming.theme.YumHubChartLines
import com.alterjuice.user_profile.viewmodels.HistoryResults
import com.alterjuice.user_profile.viewmodels.UserHistoryScreenController
import com.alterjuice.user_profile.viewmodels.UserHistoryViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun UserHistoryScreen(
    modifier: Modifier,
    controller: UserHistoryScreenController = koinViewModel<UserHistoryViewModel>()
) {
    LaunchedEffect(Unit) {
        controller.loadTodayData()
    }
    val uiState by controller.uiState.collectAsState()
    val datePickerIsVisibleState = remember { mutableStateOf(false) }
    Column(
        modifier = modifier
    ) {
        YumHubCalendarBar(
            modifier = Modifier.fillMaxWidth(),
            historyDate = uiState.historyDate,
            contentPaddings = PaddingValues(vertical = 18.dp, horizontal = 16.dp),
            onClick = rememberLambda {
                datePickerIsVisibleState.value = true
            }
        )
        Spacer(Modifier.fillMaxWidth().height(50.dp))
        when (val it = uiState.userHistoryResults) {
            is HistoryResults.Range -> {
                YumHubOutlinedCard(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp),
                    backgroundAlpha = 0.5f,
                    contentPaddingValues = PaddingValues(10.dp),
                ) {
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        verticalArrangement = Arrangement.spacedBy(5.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Int.MIN_VALUE
                        UserHistoryVicoChartWithTitle(
                            modifier = Modifier.fillMaxWidth(),
                            model = it.waterBalanceModel,
                            title = remember { "Water balance history" },
                            lineColors = remember { listOf(
                                YumHubChartLines.waterChartLineColor
                            ) }
                        )
                        UserHistoryVicoChartWithTitle(
                            modifier = Modifier.fillMaxWidth(),
                            model = it.nutrientsModel,
                            title = remember { "Nutrients history" },
                            lineColors = remember { listOf(
                                YumHubChartLines.waterChartLineColor,
                                YumHubChartLines.waterChartLineColor,
                                YumHubChartLines.waterChartLineColor,
                            ) }
                        )
                    }
                }
            }

            is HistoryResults.Single -> {
                WaterBalanceWithLeadingIconComponent(
                    waterConsumed = it.waterBalance?.balanceML ?: 0,
                )
            }

            is HistoryResults.Unspecified -> {
                /* Nothing to show */
            }
        }
    }

    val datePickerIsVisible by datePickerIsVisibleState
    AnimatedVisibility(visible = datePickerIsVisible) {
        YumHubCalendarComponent(
            modifier = Modifier,
            onDismiss = { datePickerIsVisibleState.value = false },
            availableDates = emptyList(),
            historyDate = uiState.historyDate,
            historyDateReceiver = rememberLambda {
                controller.setPickedDate(it)
            }
        )
    }
}

@Composable
@Preview
private fun UserHistoryScreenPreview() {
    UserHistoryScreen(
        modifier = Modifier
    )
}

fun main() {
    fun rowSumOddNumbers(n: Int): Int {
        // row number equals to numbers count, so

        val countBefore = (0 until n).sum()
        val nextStartFrom = (countBefore)*2 + 1
        val end = nextStartFrom + (n-1)*2
        return (nextStartFrom .. end step 2).sum()
    }

    println(rowSumOddNumbers(1))
    println(rowSumOddNumbers(1))
    println(rowSumOddNumbers(2))
    println(rowSumOddNumbers(3))
    println(rowSumOddNumbers(4))
}