package com.alterjuice.compose_utils.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp


@Composable
fun YumHubOutlinedCard(
    modifier: Modifier,
    backgroundAlpha: Float = 1f,
    color: Color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = backgroundAlpha),
    contentPaddingValues: PaddingValues = PaddingValues(16.dp),
    borderStroke: BorderStroke? = BorderStroke(2.dp, MaterialTheme.colorScheme.primary),
    surfaceShape: Shape = MaterialTheme.shapes.medium,
    content: @Composable BoxScope.() -> Unit,
) {
    Surface(
        modifier = modifier,//e.padding(paddingValues),
        color = color,
        border = borderStroke,
        shape = surfaceShape,
        content = {
            Box(
                modifier = Modifier.padding(contentPaddingValues),
                content = content
            )
        }
    )
}

@Composable
fun YumHubOutlinedCardColumn(
    modifier: Modifier,
    backgroundAlpha: Float = 1f,
    color: Color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = backgroundAlpha),
    contentPaddingValues: PaddingValues = PaddingValues(16.dp),
    borderStroke: BorderStroke? = BorderStroke(2.dp, MaterialTheme.colorScheme.primary),
    surfaceShape: Shape = MaterialTheme.shapes.medium,
    verticalArrangement: Arrangement.Vertical = Arrangement.Top,
    horizontalAlignment: Alignment.Horizontal = Alignment.Start,
    content: @Composable ColumnScope.() -> Unit,
) {
    YumHubOutlinedCard(
        modifier = modifier,
        backgroundAlpha = backgroundAlpha,
        color = color,
        contentPaddingValues = contentPaddingValues,
        borderStroke = borderStroke,
        surfaceShape = surfaceShape,
    ) {
        Column(
            verticalArrangement = verticalArrangement,
            horizontalAlignment = horizontalAlignment
        ) {
            content()
        }
    }
}

@Composable
fun YumHubOutlinedCardColumnWithCardTitle(
    modifier: Modifier,
    title: String,
    subTitle: String? = null,
    backgroundAlpha: Float = 1f,
    color: Color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = backgroundAlpha),
    contentPaddingValues: PaddingValues = PaddingValues(16.dp),
    borderStroke: BorderStroke? = BorderStroke(2.dp, MaterialTheme.colorScheme.primary),
    surfaceShape: Shape = MaterialTheme.shapes.medium,
    verticalArrangement: Arrangement.Vertical = Arrangement.Top,
    horizontalAlignment: Alignment.Horizontal = Alignment.Start,
    content: @Composable ColumnScope.() -> Unit,
) {
    YumHubOutlinedCardColumn(
        modifier = modifier,
        backgroundAlpha = backgroundAlpha,
        verticalArrangement = verticalArrangement,
        horizontalAlignment = horizontalAlignment,
        surfaceShape = surfaceShape,
        borderStroke = borderStroke,
        contentPaddingValues = contentPaddingValues,
        color = color,
    ) {
        YumHubOutlinedCardColumn(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Text(
                text = remember(title) { title },
                style = MaterialTheme.typography.titleLarge
            )
            subTitle?.let {
                Text(
                    text = remember(subTitle) { subTitle },
                    style = MaterialTheme.typography.titleSmall
                )
            }
        }
        content()
    }
}

@Composable
@Preview
private fun YumHubOutlinedCardPreview() {
    YumHubOutlinedCard(
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp)
    ) {}
}