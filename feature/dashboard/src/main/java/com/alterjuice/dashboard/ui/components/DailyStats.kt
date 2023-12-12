package com.alterjuice.dashboard.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.alterjuice.theming.theme.extension.elevationsSchema


@Composable
fun DailyStats(
    modifier: Modifier,
    dailyCarbs: State<Pair<Double, Double>>,
    dailyProtein: State<Pair<Double, Double>>,
    dailyFat: State<Pair<Double, Double>>,
) {
    Surface(
        modifier = modifier,
        shape = MaterialTheme.shapes.medium,
        shadowElevation = MaterialTheme.elevationsSchema.extraSmall
    ) {
        Row(
            modifier = Modifier
                .wrapContentSize()
                .padding(horizontal = 16.dp, vertical = 5.dp),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            DailyColumnStats(
                title = remember { "Carbs" },
                modifier = Modifier.weight(1f),
                dailyVsRecommendedDozeState = dailyCarbs
            )
            DailyColumnStats(
                title = remember { "Protein" },
                modifier = Modifier.weight(1f),
                dailyVsRecommendedDozeState = dailyProtein
            )
            DailyColumnStats(
                title = remember { "Fat" },
                modifier = Modifier.weight(1f),
                dailyVsRecommendedDozeState = dailyFat
            )
        }
    }
}