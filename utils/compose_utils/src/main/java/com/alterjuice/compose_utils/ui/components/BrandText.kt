package com.alterjuice.compose_utils.ui.components

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.alterjuice.resources.R

@Composable
@Preview("BrandText1")
fun BrandText(
    modifier: Modifier = Modifier
) {
    Text(
        text = remember { "YumHub" },
        modifier = Modifier,
        color = Color(0xFF597719),
        fontStyle = FontStyle(R.font.kyiv_type_sans_regular),
        fontWeight = FontWeight.W300,
        lineHeight = 24.sp
    )
}

@Composable
@Preview("BrandText2")
fun BrandText2(
    modifier: Modifier = Modifier
) {
    Text(
        text = buildAnnotatedString {
            withStyle(style = SpanStyle(color = Color(0xFF91B545))){
                append("Yum")
            }
            withStyle(style = SpanStyle(color = Color(0xFF597719))){
                append("Hub")
            }
        },
        modifier = modifier,
        color = Color.Unspecified,
        fontStyle = FontStyle(R.font.kyiv_type_sans_regular),
        style = MaterialTheme.typography.headlineSmall
    )
}