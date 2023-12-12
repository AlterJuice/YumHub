package com.alterjuice.dashboard.ui.screens

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import com.alterjuice.compose_utils.ui.components.SurfaceWithConstraints
import com.alterjuice.compose_utils.ui.extensions.drawColoredShadowByShape
import com.alterjuice.compose_utils.ui.extensions.rememberCreateRef
import com.alterjuice.dashboard.ui.components.AddMealsComponent
import com.alterjuice.dashboard.ui.components.AddWaterBalance
import com.alterjuice.dashboard.ui.components.DailyStats
import com.alterjuice.dashboard.viewmodels.DashboardScreenController
import com.alterjuice.dashboard.viewmodels.DashboardViewModel
import com.alterjuice.navigation.routes.DefinedRoutes
import com.alterjuice.theming.theme.LocalAppNavController
import com.alterjuice.theming.theme.YumHubTheme
import com.alterjuice.theming.theme.extension.primaryVariantSchema
import org.koin.androidx.compose.koinViewModel

private val defaultSpaceBetweenDashboardItems = 12.dp


@Composable
fun DashboardScreen(
    modifier: Modifier,
    vm: DashboardScreenController = koinViewModel<DashboardViewModel>()
) = ConstraintLayout(
    modifier = modifier
        .verticalScroll(rememberScrollState())
        .padding(horizontal = 16.dp)
        .padding(vertical = 20.dp),
) {
    val mainCircleRef = rememberCreateRef()
    val dailyStatsRef = rememberCreateRef()
    val addWaterBalance = rememberCreateRef()
    val addMealsComponentRef = rememberCreateRef()

    val uiState by vm.uiState.collectAsState()

    // ################ Daily Kcal
    SurfaceWithConstraints(
        shape = CircleShape,
        color = MaterialTheme.primaryVariantSchema.color,
        propagateMinConstraints = false,
        modifier = Modifier
            .constrainAs(mainCircleRef) {
                top.linkTo(parent.top)
                centerHorizontallyTo(parent)
            }
            .fillMaxWidth(0.5f)
            .aspectRatio(1f, true)
            .drawColoredShadowByShape(
                color = MaterialTheme.primaryVariantSchema.color,
                shape = CircleShape,
                density = LocalDensity.current,
                alpha = 0.8f,
                shadowRadius = 10.dp
            )

    ) {
        val energyForToday = uiState.dailyNutrients.calories
        Text(
            modifier = Modifier.align(Alignment.Center),
            text = remember(energyForToday) {
                "${energyForToday} kcal"
            },
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.primaryVariantSchema.onColor
        )
    }



    // ################ Daily Stats
    val dailyCarbs = remember(uiState.dailyNutrients.carbs) {
        mutableStateOf(uiState.dailyNutrients.carbs to 143.0)
    }
    val dailyProtein = remember(uiState.dailyNutrients.protein) {
        mutableStateOf(uiState.dailyNutrients.protein to 57.0)
    }
    val dailyFat = remember(uiState.dailyNutrients.fat) {
        mutableStateOf(uiState.dailyNutrients.fat to 38.0)
    }
    DailyStats(
        modifier = Modifier
            .constrainAs(dailyStatsRef) {
                top.linkTo(mainCircleRef.bottom, margin = defaultSpaceBetweenDashboardItems)
                centerHorizontallyTo(parent)
            }
            .border(
                width = Dp.Hairline,
                color = MaterialTheme.colorScheme.primary,
                shape = MaterialTheme.shapes.medium
            ),
        dailyCarbs = dailyCarbs,
        dailyFat = dailyFat,
        dailyProtein = dailyProtein
    )

    // ############ Water Balance

    AddWaterBalance(
        modifier = Modifier
            .constrainAs(addWaterBalance) {
                top.linkTo(dailyStatsRef.bottom, margin = defaultSpaceBetweenDashboardItems)
                centerHorizontallyTo(parent)
            }
            .fillMaxWidth()
        ,
        recommended = uiState.recommendedWaterConsumption,
        consumedMLs = uiState.dailyWaterBalance,
        onNewWaterBalance = vm::updateTodayWaterBalance
    )

    // ############ Add meals
    val navController = LocalAppNavController.current
    AddMealsComponent(Modifier.constrainAs(addMealsComponentRef) {
        top.linkTo(addWaterBalance.bottom, margin = defaultSpaceBetweenDashboardItems)
        centerHorizontallyTo(parent)
    }) { mealType ->
        navController.navigate(DefinedRoutes.AddMeal(mealType))
    }
}


@Composable
@Preview
private fun DashboardScreenPreview() {
    YumHubTheme() {
        DashboardScreen(
            modifier = Modifier
        )
    }
}