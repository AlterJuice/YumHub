package com.alterjuice.compose_utils.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.alterjuice.domain.model.common.YumHubMeal
import com.alterjuice.domain.model.nutrition.NutritionEnum
import com.alterjuice.theming.theme.extension.primaryVariantSchema
import com.alterjuice.utils.DateTimeUtils

@Composable
fun YumHubMealCard(
    modifier: Modifier,
    meal: YumHubMeal,
    onClick: () -> Unit = {}
) {
    YumHubOutlinedCard(
        modifier = modifier,
        contentPaddingValues = PaddingValues(12.dp),
        backgroundAlpha = 0.5f
    ) {
        Column(Modifier.fillMaxWidth()) {

            Text(
                text = remember(meal) { meal.foodName },
                style = MaterialTheme.typography.titleMedium,
                softWrap = true,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.fillMaxWidth()
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Top
            ) {
                Box(
                    modifier = Modifier
                        .size(90.dp)
                        .background(
                            color = MaterialTheme.primaryVariantSchema.color,
                            shape = CircleShape
                        )
                )

                Column(
                    Modifier.fillMaxSize()
                ) {
                    Text(
                        text = remember(meal){ buildString {
                            append("1 serving nutrients:\n")
                            append("Protein: ${meal.withServingsWithUnits(1.0, NutritionEnum.Protein)}\n")
                            append("Fat: ${meal.withServingsWithUnits(1.0, NutritionEnum.Fat)}\n")
                        }},
                        style = MaterialTheme.typography.bodyMedium,
                        lineHeight = 15.sp
                    )

                    meal.cookInfo?.let { cookInfo ->
                        Column(
                            modifier = Modifier.fillMaxSize(),
                            verticalArrangement = Arrangement.spacedBy(1.dp, alignment = Alignment.Bottom),
                            horizontalAlignment = Alignment.End
                        ) {
                            CompositionLocalProvider(
                                LocalTextStyle provides MaterialTheme.typography.labelMedium
                            ) {
                                cookInfo.cookTime?.takeIf { it != 0L }?.let { cookTime ->
                                    Text("Cook time: ${DateTimeUtils.getDurationFromSec(cookTime)}")
                                }
                                cookInfo.prepareTime?.takeIf { it != 0L }?.let { prepareTime ->
                                    Text("Prepare time: ${DateTimeUtils.getDurationFromSec(prepareTime)}")
                                }
                                cookInfo.waitTime?.takeIf { it != 0L }?.let { waitTime ->
                                    Text("Wait time: ${DateTimeUtils.getDurationFromSec(waitTime)}")
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
@Preview
private fun YumHubMealCardPreview() {
    YumHubMealCard(
        modifier = Modifier.size(340.dp, 180.dp),
        meal = YumHubMeal.empty("Empty")
    )
}