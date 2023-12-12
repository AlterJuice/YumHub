package com.alterjuice.meals.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ProvideTextStyle
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.alterjuice.domain.model.common.YumHubMeal
import com.alterjuice.domain.model.nutrition.NutrientsItem
import com.alterjuice.utils.extensions.gracefulRound

@Composable
fun MealInfoCard(
    modifier: Modifier,
    meal: YumHubMeal
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {

        Text(
            text = remember(meal) { meal.foodName },
            style = MaterialTheme.typography.headlineMedium
        )
        var showAllNutrients by remember { mutableStateOf(false) }
        val servingsCount = remember(meal) {
            meal.servingsCount
        }
        val servingIsSingle = remember(servingsCount) {
            servingsCount <= 1.0
        }
        val nutrientsToShow = remember(meal, showAllNutrients) {
            val items = if (showAllNutrients) {
                meal.nutrients
            } else {
                meal.mainNutrients.filterNotNull()
            }
            items.sortedBy { it.attr.importanceKey }
        }
        val colorScheme = MaterialTheme.colorScheme
        val viewNutrientsText = remember(showAllNutrients) {
            val text = if (showAllNutrients) {
                "See main nutrients"
            } else {
                "See all nutrients"
            }

            buildAnnotatedString {
                pushStringAnnotation(tag = "view_nutrients", annotation = "view_nutrients")
                withStyle(
                    style = SpanStyle(
                        color = colorScheme.primary,
                        textDecoration = TextDecoration.Underline
                    )
                ) {
                    append(text)
                }
                pop()
            }
        }

        LazyColumn(
            modifier = Modifier.padding(bottom = 32.dp),
        ) {
            item {
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
            }

            item {
                Spacer(
                    Modifier
                        .fillMaxWidth()
                        .height(10.dp))
            }

            items(nutrientsToShow) { nutrient ->
                NutrientRowInfo(
                    modifier = Modifier.fillMaxWidth(),
                    totalServingsCount = meal.servingsCount,
                    nutrient = nutrient,
                    servingIsSingle = servingIsSingle
                )
            }
            item {
                Spacer(
                    Modifier
                        .fillMaxWidth()
                        .height(10.dp))
            }
            item {
                ClickableText(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 10.dp),
                    style = MaterialTheme.typography.titleLarge,
                    text = viewNutrientsText,
                    onClick = {
                        viewNutrientsText.getStringAnnotations(tag = "view_nutrients", it, it).firstOrNull()?.let {
                            showAllNutrients = !showAllNutrients
                        }
                    })
            }
        }
    }
}

@Composable
fun NutrientRowInfo(
    modifier: Modifier,
    totalServingsCount: Double,
    nutrient: NutrientsItem,
    servingIsSingle: Boolean,
) {
    val nutrientValue = remember(nutrient.attr.attrID) {
        nutrient.value?: 0.0
    }
    val oneServingNutrients = remember(nutrientValue) {
        nutrientValue / totalServingsCount
    }
    NutrientItemRow(
        modifier = modifier,
        textTitle = remember(nutrient.attr.attrID) {
            nutrient.attr.nutritionName
        },
        textOnOneServing = remember(oneServingNutrients) {
            "${oneServingNutrients.gracefulRound()} ${nutrient.attr.unit}"
        },
        textOnNonSingleServingsCount = remember(nutrient.attr.attrID) {
            "${nutrientValue.gracefulRound()} ${nutrient.attr.unit}"
        },
        servingIsSingle = servingIsSingle
    )
}

@Composable
fun NutrientItemRow(
    modifier: Modifier,
    textTitle: String,
    textOnOneServing: String,
    textOnNonSingleServingsCount: String,
    servingIsSingle: Boolean,
    style: TextStyle = MaterialTheme.typography.bodyLarge,
) {
    ProvideTextStyle(style.copy(textAlign = TextAlign.Center)) {
        Row(
            modifier = modifier,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = textTitle,
                modifier = Modifier.weight(1f),
            )
            if (!servingIsSingle) {
                Text(
                    text = textOnNonSingleServingsCount,
                    modifier = Modifier.weight(1f),
                )
            }
            Text(
                text = textOnOneServing,
                modifier = Modifier.weight(1f),
            )
        }
    }
}

@Composable
@Preview
private fun MealCardPreview() {
    MealInfoCard(
        modifier = Modifier,
        meal = remember { YumHubMeal.empty("Empty meal") }
    )
}