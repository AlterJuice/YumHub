package com.alterjuice.dashboard.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.alterjuice.compose_utils.ui.extensions.rememberLambda
import com.alterjuice.domain.model.common.MealType
import com.alterjuice.resources.R

@Composable
fun AddMealsComponent(
    modifier: Modifier,
    onClick: (MealType) -> Unit
) {
    val itemsSpacer = remember { 5.dp }

    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(itemsSpacer)
    ) {
        val rowModifier = Modifier
            .fillMaxWidth()
            .height(60.dp)
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(itemsSpacer)
        ) {
            AddMealComponent(
                modifier = rowModifier.weight(1f),
                title = remember { "Add Breakfast" },
                onClick = rememberLambda {
                    onClick(MealType.Breakfast)
                }
            )
            AddMealComponent(
                modifier = rowModifier.weight(1f),
                title = remember { "Lunch" },
                onClick = rememberLambda {
                    onClick(MealType.Lunch)
                }
            )
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(itemsSpacer)
        ) {
            AddMealComponent(
                modifier = rowModifier.weight(1f),
                title = remember { "Dinner" },
                onClick = rememberLambda {
                    onClick(MealType.Dinner)
                }
            )
            AddMealComponent(
                modifier = rowModifier.weight(1f),
                title = remember { "Supper" },
                onClick = rememberLambda {
                    onClick(MealType.Supper)
                }
            )
        }
        AddMealComponent(
            modifier = rowModifier,
            title = remember { "Snack" },
            arrangeToCenter = remember { true },
            onClick = rememberLambda {
                onClick(MealType.Snack)
            }
        )
    }
}

@Composable
fun AddMealComponent(
    modifier: Modifier,
    title: String,
    arrangeToCenter: Boolean = false,
    onClick: () -> Unit,

    ) {
    val arrangement = remember(arrangeToCenter) {
        if (arrangeToCenter) {
            Arrangement.spacedBy(10.dp, Alignment.CenterHorizontally)
        } else {
            Arrangement.spacedBy(10.dp, Alignment.Start)
        }
    }
    FilledIconButton(
        modifier = modifier,
        onClick = onClick,

        ) {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 10.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = arrangement
        ) {

            Icon(
                painter = painterResource(id = R.drawable.round_add_circle_24),
                contentDescription = null,
                modifier = Modifier
                    .size(40.dp)
                    .aspectRatio(1f)
            )

            Text(
                modifier = Modifier,
                text = remember(title) { title },
                style = MaterialTheme.typography.bodyLarge,
            )

        }
    }
}

@Composable
@Preview
private fun AddMealsComponentPreview() {
    AddMealsComponent(
        modifier = Modifier,
        onClick = {}
    )
}