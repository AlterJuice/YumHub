package com.alterjuice.compose_utils.ui.extensions

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.offset
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import kotlinx.coroutines.delay

fun Modifier.surface(
    shape: Shape,
    backgroundColor: Color,
    border: BorderStroke?,
    shadowElevation: Dp,
) = this
    .shadow(shadowElevation, shape, clip = false)
    .then(if (border != null) Modifier.border(border, shape) else Modifier)
    .background(color = backgroundColor, shape = shape)
    .clip(shape)


fun Modifier.animatedShadowAppearingOnPressByShape(
    isPressed: Boolean,
    color: Color,
    shape: Shape,
    maxAlpha: Float = 1f,
    shadowRadius: Dp,
): Modifier = composed {
    val density = LocalDensity.current

    val alpha by animateFloatAsState(if (isPressed) maxAlpha else 0f)
    this.drawColoredShadowByShape(
        color = color,
        alpha = alpha,
        density = density,
        shadowRadius = shadowRadius,
        shape = shape
    )
}


@Composable
fun DraggableModifier() = remember { mutableStateOf(Offset.Zero) }.let { position ->
    Modifier
        .offset { IntOffset(position.value.x.toInt(), position.value.y.toInt()) }
        .pointerInput(Unit) {
            detectDragGestures { change, dragAmount ->
                position.value += dragAmount
                change.consume()

            }
        }
}

fun Modifier.scalingAnimationModifier(
    scaleFrom: Float,
    scaleTo: Float,
    scaleAnimationDurationMs: Int,
) = composed {
    val initial = remember { Animatable(scaleFrom) }
    LaunchedEffect(Unit) {
        val randomDelay = (300L..2000L).random()
        delay(randomDelay)
        initial.animateTo(scaleTo, infiniteRepeatable(
            animation = tween(
                durationMillis = scaleAnimationDurationMs,
                easing = LinearEasing,
            ), RepeatMode.Reverse),
        )
    }
    val scale by initial.asState()
    Modifier.scale(scale)
}

fun Modifier.offsetBy(
    offset: Offset
) = this.offset {
    IntOffset(
        offset.x.toInt(),
        offset.y.toInt()
    )
}
