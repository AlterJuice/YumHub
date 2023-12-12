package com.alterjuice.meals.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.alterjuice.compose_utils.ui.components.YumHubMealCard
import com.alterjuice.compose_utils.ui.extensions.rememberLambda
import com.alterjuice.meals.viewmodels.MealsViewModel
import com.alterjuice.navigation.routes.DefinedRoutes
import com.alterjuice.theming.theme.LocalAppNavController
import org.koin.androidx.compose.koinViewModel

@Composable
fun MealsScreen(
    modifier: Modifier,
    controller: MealsViewModel = koinViewModel<MealsViewModel>()
) {

    val categories by controller.actualCategories.collectAsState()
    val navController = LocalAppNavController.current
    val recommended by controller.recommendations.collectAsState()
    LaunchedEffect(Unit) {
        controller.updatedRecommendations()
    }
    Column {


        LazyHorizontalGrid(rows = GridCells.Fixed(2)) {
            this.items(recommended) { meal ->
                YumHubMealCard(
                    modifier = Modifier.widthIn(min = 140.dp, max = 320.dp),
                    meal = meal,
                    onClick = rememberLambda {
                        navController.navigate(DefinedRoutes.MealPage(meal.id))
                    }
                )
            }
        }
        LazyColumn(
            modifier = Modifier.fillMaxSize()

        ) {

            items(categories) { category ->
                val dishes = remember(category) { controller.getDishesByCategory(category) }

                Text(
                    modifier = Modifier.padding(start = 16.dp),
                    text = "Category: ${category.name}"
                )
                LazyRow(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    item {
                        Spacer(Modifier.width(16.dp))
                    }
                    items(dishes) { dish ->
                        YumHubMealCard(
                            modifier = Modifier.widthIn(min = 140.dp, max = 320.dp),
                            meal = dish,
                            onClick = rememberLambda {
                                navController.navigate(DefinedRoutes.MealPage(dish.id))
                            }
                        )
                    }
                }
            }
        }
    }
}

@Composable
@Preview
private fun MealsScreenPreview() {
    MealsScreen(
        modifier = Modifier
    )
}