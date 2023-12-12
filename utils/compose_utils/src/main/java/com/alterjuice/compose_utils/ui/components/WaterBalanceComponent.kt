package com.alterjuice.compose_utils.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.alterjuice.resources.R
import com.alterjuice.utils.WaterMLFormatter

val DefaultDropValue = 200


@Composable
fun WaterBalanceWithLeadingIconComponent(
    waterConsumed: Int?
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                color = MaterialTheme.colorScheme.surfaceVariant,
                shape = MaterialTheme.shapes.medium
            )
            .border(
                width = 2.dp,
                color = MaterialTheme.colorScheme.primary,
                shape = MaterialTheme.shapes.medium
            )
            .padding(10.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(10.dp, Alignment.Start)
    ) {
        Icon(
            modifier = Modifier.size(30.dp),
            tint = Color.Unspecified,
            painter = painterResource(id = R.drawable.water_drop_filled),
            contentDescription = null,
        )

        Text(
            text = remember(waterConsumed) {
                val balanceTransformed = waterConsumed?.toString() ?: "-"
                "Balance: ${balanceTransformed}ML"
            },
            style = MaterialTheme.typography.bodyLarge
        )
    }
}

@Composable
fun WaterBalanceComponent(
    waterConsumed: Int?,
    waterRecommended: Int? = null,
    showWaterDropContent: Boolean = true,
) {
    Column(
        modifier = Modifier.fillMaxWidth()
            .background(
                color = MaterialTheme.colorScheme.surfaceVariant,
                shape = MaterialTheme.shapes.medium
            )
            .border(
                width = 2.dp,
                color = MaterialTheme.colorScheme.primary,
                shape = MaterialTheme.shapes.medium
            )
            .padding(10.dp)
    ){
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = remember(waterConsumed) {
                    val balanceTransformed = waterConsumed?.let {
                        WaterMLFormatter.mlToLiters(it)
                    } ?: "-"
                    "Water balance: $balanceTransformed"
                },
                style = MaterialTheme.typography.bodyLarge
            )
            if (showWaterDropContent) {
                Row(
                    modifier = Modifier,
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Icon(
                        modifier = Modifier.size(30.dp),
                        tint = Color.Unspecified,
                        painter = painterResource(id = R.drawable.water_drop_filled),
                        contentDescription = null,
                    )
                    Text(
                        text = remember { " = ${DefaultDropValue}ML" },
                        style = MaterialTheme.typography.bodyLarge
                    )
                }
            }
        }
        waterRecommended?.let { recommended ->
            Text(
                text = remember { "Personal water consumption recommendations: ${WaterMLFormatter.mlToLiters(recommended)}" },
                style = MaterialTheme.typography.labelSmall
            )
        }
    }
}


@Preview
@Composable
fun WaterBalanceWithLeadingIconComponentEmptyPreview() {
    WaterBalanceWithLeadingIconComponent(null)
}

@Preview
@Composable
fun WaterBalanceWithLeadingIconComponentPreview() {
    WaterBalanceWithLeadingIconComponent(1400)
}

@Preview
@Composable
fun WaterBalanceComponentWithIconPreview() {
    WaterBalanceComponent(1400)
}

@Preview
@Composable
fun WaterBalanceComponentWithoutIconPreview() {
    WaterBalanceComponent(1400, showWaterDropContent = false)
}