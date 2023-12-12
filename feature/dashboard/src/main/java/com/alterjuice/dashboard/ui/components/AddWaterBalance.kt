package com.alterjuice.dashboard.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.alterjuice.compose_utils.ui.components.DefaultDropValue
import com.alterjuice.compose_utils.ui.components.WaterBalanceComponent
import com.alterjuice.compose_utils.ui.components.YumHubOutlinedCard
import com.alterjuice.compose_utils.ui.extensions.rememberLambda
import com.alterjuice.utils.extensions.divMod

@Composable
fun AddWaterBalance(
    modifier: Modifier,
    consumedMLs: Int,
    recommended: Int? = null,
    onNewWaterBalance: (Int) -> Unit
) {

    val dropValue = remember { DefaultDropValue }
    val dropsPerRow = remember { 7 }
    val initialRowsCount = remember { 2 }

    val dropsCount = remember(consumedMLs) {
        consumedMLs / dropValue
    }
    val rowsCount = remember(dropsCount) {
        val divMod = dropsCount.divMod(dropsPerRow)
        val result = divMod.first + 1
        result.coerceAtLeast(initialRowsCount)
    }

    val onDropClick: (dropIndex: Int) -> Unit = rememberLambda {
        val newConsumed = (it + 1) * dropValue
        onNewWaterBalance(newConsumed)
    }

    YumHubOutlinedCard(
        modifier = modifier,
        backgroundAlpha = 0.5f,
        contentPaddingValues = PaddingValues(10.dp)
    ) {
        Column {
            WaterBalanceComponent(
                waterConsumed = consumedMLs,
                waterRecommended = recommended,
            )
            Spacer(Modifier.height(10.dp))
            repeat(rowsCount) { rowIndex ->
                LazyRow(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    items(dropsPerRow) { itemIndex ->
                        val dropIndex = remember(rowIndex, itemIndex) {
                            rowIndex * dropsPerRow + itemIndex
                        }
                        val isFilled = remember(dropsCount) {
                            dropIndex < dropsCount
                        }
                        val icon = remember(isFilled) {
                            if (isFilled) {
                                com.alterjuice.resources.R.drawable.water_drop_filled
                            } else {
                                com.alterjuice.resources.R.drawable.water_drop_empty
                            }
                        }
                        IconButton(
                            onClick = rememberLambda { onDropClick(dropIndex) }
                        ) {
                            Icon(
                                tint = Color.Unspecified,
                                painter = painterResource(id = icon),
                                contentDescription = null
                            )
                        }
                    }
                }
            }
        }
    }
}


@Composable
@Preview
private fun AddWaterBalancePreview() {
    AddWaterBalance(
        modifier = Modifier,
        consumedMLs = remember { 2300 },
        onNewWaterBalance = { /* Nothing to do */ }
    )
}