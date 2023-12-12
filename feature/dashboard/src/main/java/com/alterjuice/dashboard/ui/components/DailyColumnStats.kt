package com.alterjuice.dashboard.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.alterjuice.compose_utils.ui.extensions.rememberDerivedStateOf

@Composable
fun DailyColumnStats(
    modifier: Modifier,
    title: String,
    dailyVsRecommendedDozeState: State<Pair<Double, Double>>,
) {
    val dailyVsRecommended by dailyVsRecommendedDozeState
    val onePercent = remember(dailyVsRecommended) { dailyVsRecommended.second.toFloat().div(100f) }
    val percents by rememberDerivedStateOf(dailyVsRecommended) {
        dailyVsRecommended.first.div(onePercent).div(100).toFloat()
    }

    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = remember(title) { title },
            style = MaterialTheme.typography.titleSmall,
        )
        LinearProgressIndicator(progress = percents)
        Text(
            text = remember(dailyVsRecommended) {
                "${dailyVsRecommended.first.toInt()}/${dailyVsRecommended.second.toInt()}g"
            },
            style = MaterialTheme.typography.labelSmall,
        )
    }
}