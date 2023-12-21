package com.alterjuice.meals.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.rememberNestedScrollInteropConnection
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.alterjuice.compose_utils.ui.components.YumHubMealCard
import com.alterjuice.compose_utils.ui.components.YumHubOutlinedCardColumn
import com.alterjuice.compose_utils.ui.components.YumHubOutlinedCardColumnWithCardTitle
import com.alterjuice.compose_utils.ui.extensions.rememberLambda
import com.alterjuice.data.analyzers.AbsoluteDifferenceSimilarityAnlysis
import com.alterjuice.data.data.getMealWithRecipeItemsAsYumHubMeals
import com.alterjuice.domain.model.common.YumHubMeal
import com.alterjuice.meals.ui.components.NutrientItemRow
import com.alterjuice.meals.ui.components.NutrientRowInfo
import com.alterjuice.navigation.routes.DefinedRoutes
import com.alterjuice.utils.extensions.gracefulRound

@Composable
fun MealInfoScreen(
    modifier: Modifier,
    yumHubMeal: YumHubMeal
) {
    val similarProducts = remember { mutableStateOf(emptyList<YumHubMeal>())}
    LaunchedEffect(Unit) {
        val products = getMealWithRecipeItemsAsYumHubMeals()
        val index = products.indexOfFirst {
            it.id == yumHubMeal.id
        }
        val similarProductsMatrix = AbsoluteDifferenceSimilarityAnlysis.createSimilarityMatrixBetweenMeals(
            products = products
        ).get(index).withIndex().associate {
            products.get(it.index) to it.value
        }.toList().sortedByDescending { it.second }.map { it.first }
        similarProducts.value = similarProductsMatrix
    }
    YumHubOutlinedCardColumnWithCardTitle(
        modifier = modifier.verticalScroll(rememberScrollState()),
        title = remember(yumHubMeal) {
            yumHubMeal.foodName
        },
        subTitle = null,
        borderStroke = null,
        backgroundAlpha = 0.5f,
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        YumHubOutlinedCardColumnWithCardTitle(
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .fillMaxWidth()
                .nestedScroll(rememberNestedScrollInteropConnection()),
            title = remember(yumHubMeal) {
                "Nutrients"
            },
            subTitle = null,
            backgroundAlpha = 0.5f,
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            val nutrientsToShow = remember(yumHubMeal) {
                yumHubMeal.mainNutrients.filterNotNull().sortedBy {
                    it.attr.importanceKey
                }
            }
            val servingsCount = remember(yumHubMeal) {
                yumHubMeal.servingsCount
            }
            val servingIsSingle = remember(servingsCount) {
                servingsCount <= 1.0
            }
            NutrientItemRow(
                modifier = Modifier.fillMaxWidth(),
                textTitle = remember { "Nutrition" },
                textOnOneServing = remember { "1 serving" },
                textOnNonSingleServingsCount = remember(servingsCount) {
                    "${servingsCount.gracefulRound()} servings"
                },
                servingIsSingle = servingIsSingle,
                style = MaterialTheme.typography.titleLarge
            )
            Spacer(
                Modifier
                    .fillMaxWidth()
                    .height(10.dp)
            )
            nutrientsToShow.forEach { nutrient ->
                NutrientRowInfo(
                    modifier = Modifier.fillMaxWidth(),
                    totalServingsCount = yumHubMeal.servingsCount,
                    nutrient = nutrient,
                    servingIsSingle = servingIsSingle
                )
            }
        }
        when (val cookInfo = yumHubMeal.cookInfo) {
            null -> {}
            else -> {
                YumHubOutlinedCardColumnWithCardTitle(
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .fillMaxWidth()
                        .nestedScroll(rememberNestedScrollInteropConnection()),
                    title = remember(yumHubMeal) {
                        "Preparation & Cook information"
                    },
                    subTitle = null,
                    backgroundAlpha = 0.5f,
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    YumHubOutlinedCardColumn(
                        modifier = Modifier
                            .align(Alignment.CenterHorizontally)
                            .fillMaxWidth()
                            .nestedScroll(rememberNestedScrollInteropConnection()),
                        backgroundAlpha = 0.5f,
                    ) {
                        Text(
                            text = remember { "Ingredients" },
                            style = MaterialTheme.typography.titleLarge
                        )
                        cookInfo.ingredients.forEach { ingredient ->
                            Text(text = ingredient.replace("<hr>", ""))
                        }
                    }
                    YumHubOutlinedCardColumn(
                        modifier = Modifier
                            .align(Alignment.CenterHorizontally)
                            .fillMaxWidth()
                            .nestedScroll(rememberNestedScrollInteropConnection()),
                        backgroundAlpha = 0.5f,
                    ) {
                        Text(
                            text = remember { "Recipe" },
                            style = MaterialTheme.typography.titleLarge
                        )
                        Text(text = cookInfo.instructions.orEmpty())
                    }
                }
            }
        }

        YumHubOutlinedCardColumn(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Text(
                text = remember { "Similar dishes" },
                style = MaterialTheme.typography.titleLarge
            )
            LazyHorizontalGrid(
                modifier = Modifier.height(400.dp),
                rows = GridCells.Fixed(2),
                horizontalArrangement = Arrangement.spacedBy(10.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp),
                contentPadding = PaddingValues(start = 0.dp)
            ) {

                this.items(similarProducts.value) { meal ->
                    YumHubMealCard(
                        modifier = Modifier.widthIn(min = 140.dp, max = 250.dp),
                        meal = meal,
                        onClick = rememberLambda { }
                    )
                }
            }
        }
    }
}

@Composable
@Preview
private fun MealInfoScreenPreview() {
    MealInfoScreen(
        modifier = Modifier,
        yumHubMeal = YumHubMeal.empty("Empty one")
    )
}