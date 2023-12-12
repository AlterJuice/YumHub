@file:OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterial3Api::class)

package com.alterjuice.user_profile.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ProvideTextStyle
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.alterjuice.compose_utils.ui.components.YumHubOutlinedCardColumn
import com.alterjuice.compose_utils.ui.components.charts.UserHistoryVicoChart
import com.alterjuice.compose_utils.ui.components.charts.UserHistoryVicoChartWithTitle
import com.alterjuice.domain.model.user.UserSex
import com.alterjuice.theming.theme.YumHubChartLines
import com.alterjuice.theming.theme.extension.primaryVariantSchema
import com.alterjuice.user_profile.viewmodels.UserProfileScreenController
import com.alterjuice.user_profile.viewmodels.UserProfileViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun UserProfileScreen(
    modifier: Modifier,
    controller: UserProfileScreenController = koinViewModel<UserProfileViewModel>()
) {
    val uiState by controller.uiState.collectAsState()
    Column {
        YumHubOutlinedCardColumn(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .padding(16.dp),
            backgroundAlpha = 0.7f
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Box(
                    modifier = Modifier
                        .size(90.dp)
                        .background(
                            color = MaterialTheme.primaryVariantSchema.color,
                            shape = CircleShape
                        )
                        .align(Alignment.CenterVertically)
                )

                Column(
                    modifier = Modifier,
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Text(
                        text = "Bohdan Snurnitsyn",
                        style = MaterialTheme.typography.titleLarge
                    )
                    ProvideTextStyle(MaterialTheme.typography.bodyLarge) {
                        uiState.userInfo.let {
                            Text("+ Sex: ${(it.sex?: UserSex.UNSPECIFIED).name.toLowerCase()}")
                            Text("+ ${it.weight?:"-"}kg, ${it.height?: "-"}cm")
                            Text("+ Goals: ${it.fitnessGoal}")
                        }
                    }
                }
            }
        }

        YumHubOutlinedCardColumn(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .padding(16.dp),
            backgroundAlpha = 0.7f
        ) {
            UserHistoryVicoChartWithTitle(
                modifier = Modifier.fillMaxWidth(),
                model = uiState.measurementsModel,
                title = remember { "Measurements history" },
                lineColors = remember { listOf(
                    YumHubChartLines.waterChartLineColor
                ) }
            )
        }
    }
}

@Composable
@Preview
private fun UserProfileScreenPreview() {
    UserProfileScreen(
        modifier = Modifier
    )
}