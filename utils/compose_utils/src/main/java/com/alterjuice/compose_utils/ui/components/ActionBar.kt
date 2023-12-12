package com.alterjuice.compose_utils.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.text.BasicText
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp


@Composable
fun ActionBar(
    modifier: Modifier,
    centeredTitle: String?
) {
    SurfaceWithConstraints(
        modifier = modifier,
        shape = RectangleShape,
        color = MaterialTheme.colorScheme.secondaryContainer,
        border = null,
        shadowElevation = 10.dp,
        contentAlignment = Alignment.CenterStart
    ) {
        Box(
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 12.dp)
        ) {
            BrandText2(
                modifier = Modifier.wrapContentHeight()
            )

            centeredTitle?.let { nonNullTitle ->
                Text(
                    modifier = Modifier.align(Alignment.Center),
                    text = nonNullTitle,
                    style = MaterialTheme.typography.headlineSmall,
                    color = MaterialTheme.colorScheme.onSecondaryContainer
                )
            }
        }
    }
}

@Composable
@Preview
private fun ActionBarPreview() {
    ActionBar(
        modifier = Modifier.fillMaxWidth(),
        centeredTitle = remember {"Profile" }
    )
}