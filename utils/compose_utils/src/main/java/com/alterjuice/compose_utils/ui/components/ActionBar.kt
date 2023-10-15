package com.alterjuice.compose_utils.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.alterjuice.compose_utils.ui.extensions.surface
import com.alterjuice.resources.R


@Composable
fun ActionBar(
    modifier: Modifier
) {
    Box(
        modifier = Modifier
            .then(modifier)
            .surface(
                shape = RectangleShape,
                backgroundColor = Color(0xFFEEE9DB),
                border = null,
                elevation = 10.dp
            )
            .padding(horizontal = 16.dp, vertical = 12.dp)
        ,
        contentAlignment = Alignment.CenterStart
    ) {
        BrandText(
            modifier = Modifier
        )
    }
}

@Composable
@Preview
private fun ActionBarPreview() {
    ActionBar(
        modifier = Modifier.fillMaxWidth()
    )
}