@file:OptIn(ExperimentalMaterial3Api::class)

package com.alterjuice.meals.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.alterjuice.compose_utils.ui.extensions.rememberLambda
import com.alterjuice.compose_utils.ui.extensions.surface
import com.alterjuice.domain.model.common.MealType
import com.alterjuice.domain.model.common.YumHubMeal
import com.alterjuice.meals.ui.components.MealInfoCard
import com.alterjuice.meals.viewmodels.AddMealScreenController
import com.alterjuice.meals.viewmodels.AddMealViewModel
import com.alterjuice.theming.theme.LocalAppNavController
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel

@Composable
fun AddMealScreen(
    modifier: Modifier,
    mealType: MealType,
    controller: AddMealScreenController = koinViewModel<AddMealViewModel>()
) {
    // val addedItems by vm.addedMeals
    val navController = LocalAppNavController.current

    val sheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = true
    )
    LaunchedEffect(Unit) {
        sheetState.hide()
    }
    val coroutine = rememberCoroutineScope()
    val currentYumHubMeal = remember { mutableStateOf<YumHubMeal?>(null) }
    var showBottomSheet by remember { mutableStateOf(false) }
    val openSheetWithMeal = rememberLambda<(YumHubMeal) -> Unit> {
        currentYumHubMeal.value = it
        showBottomSheet = true
        coroutine.launch { sheetState.expand() }
    }
    val hideSheet = rememberLambda {
        coroutine.launch { sheetState.hide() }.invokeOnCompletion {
            if (!sheetState.isVisible) {
                currentYumHubMeal.value = null
                showBottomSheet = false
            }
        }
    }
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.surface.copy(alpha = 0.4f),
    ) {
        val meals by controller.allMeals.collectAsState()
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(10.dp),
        ) {
            items(
                items = meals,
                key = { it.hash }
            ) { meal ->
                Text(
                    text = remember(meal) { meal.getDescription() },
                    modifier = Modifier
                        .surface(
                            shape = MaterialTheme.shapes.medium,
                            backgroundColor = MaterialTheme.colorScheme.surface,
                            border = BorderStroke(1.dp, MaterialTheme.colorScheme.primary),
                            shadowElevation = 5.dp
                        )
                        .padding(16.dp)
                        .clickable {
                            openSheetWithMeal(meal)
                        }
                )
            }
        }
    }

    if (showBottomSheet) {
        val localMeal = remember(currentYumHubMeal.value) { currentYumHubMeal.value }
        ModalBottomSheet(
            sheetState = sheetState,
            onDismissRequest = { hideSheet() }
        ) {
            localMeal?.let {
                MealInfoCard(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 10.dp),
                    meal = localMeal
                )
                TextButton(
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .fillMaxWidth(0.6f),
                    border = BorderStroke(1.dp, MaterialTheme.colorScheme.primary),
                    onClick = rememberLambda {
                        controller.addMealToHistory(localMeal, mealType)
                        navController.navigateUp()
                    }
                ) {
                    Text(text = "Add as $mealType")
                }
                Spacer(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(16.dp)
                )
            }
        }
    }
}

@Composable
@Preview
private fun AddMealScreenPreview() {
    AddMealScreen(
        modifier = Modifier,
        mealType = MealType.Snack
    )
}