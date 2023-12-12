package com.alterjuice.compose_utils.ui.components

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.alterjuice.compose_utils.ui.extensions.drawColoredShadowByShape
import com.alterjuice.compose_utils.ui.extensions.offsetBy
import com.alterjuice.compose_utils.ui.extensions.rememberWithDensity
import com.alterjuice.compose_utils.ui.extensions.scalingAnimationModifier

@Composable
fun YumHubCircledBackground(
    content: @Composable () -> Unit
) {
    SurfaceWithConstraints(
        modifier = Modifier.fillMaxSize(),
        propagateMinConstraints = false,
    ) {

        val diameter = rememberWithDensity(maxWidth) { maxWidth.toPx().div(2f) }
        val secondCircleDiameter = remember(diameter) { diameter.div(2f) }
        val topRightCirclePosition = rememberWithDensity(maxHeight, maxWidth) {
            Offset(
                x = maxWidth.toPx() - diameter.div(4).times(3),
                y = -diameter.div(4f).times(1.25f)
            )
        }

        val bottomLeftCirclePosition = rememberWithDensity(maxHeight, maxWidth) {
            Offset(
                x = -diameter.div(4f).times(1.25f),
                y = maxHeight.toPx() - diameter.div(4).times(3),
            )
        }

        val topRightLightCirclePosition = rememberWithDensity(topRightCirclePosition) {
            Offset(
                x = maxWidth.div(2).toPx(),
                y = topRightCirclePosition.y + diameter.div(1.5f),
            )

        }
        val bottomLeftLightCirclePosition = rememberWithDensity(bottomLeftCirclePosition) {
            Offset(
                x = bottomLeftCirclePosition.x + diameter.div(1.5f),
                y = bottomLeftCirclePosition.y - diameter.div(4f),
            )

        }
        val greenCircleModifier = Modifier
            .size(230.dp)
            .scalingAnimationModifier(
                scaleFrom = 1f,
                scaleTo = 1.2f,
                scaleAnimationDurationMs = 3000,
            )
            .drawColoredShadowByShape(
                color = Color(0xFF8CB16F),
                shape = CircleShape,
                density = LocalDensity.current,
                alpha = 0.7f,
                shadowRadius = 10.dp,
            )

        val grayCircleModifier = Modifier
            .size(130.dp)
            .scalingAnimationModifier(
                scaleFrom = 1f,
                scaleTo = 1.2f,
                scaleAnimationDurationMs = 3000,
            )
            .drawColoredShadowByShape(
                color = Color(0xFFC9D2C1),
                shape = CircleShape,
                density = LocalDensity.current,
                alpha = 0.6f,
                shadowRadius = 12.dp
            )

        Spacer(
            Modifier
                .offsetBy(topRightLightCirclePosition)
                .then(grayCircleModifier))
        Spacer(
            Modifier
                .offsetBy(bottomLeftLightCirclePosition)
                .then(grayCircleModifier))
        Spacer(
            Modifier
                .offsetBy(topRightCirclePosition)
                .then(greenCircleModifier))
        Spacer(
            Modifier
                .offsetBy(bottomLeftCirclePosition)
                .then(greenCircleModifier))

        content()
    }
}


@Composable
@Preview
private fun YumHubCircledBackgroundPreview() {
    YumHubCircledBackground {}
}