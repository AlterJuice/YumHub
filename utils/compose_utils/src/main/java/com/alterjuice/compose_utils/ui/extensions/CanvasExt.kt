package com.alterjuice.compose_utils.ui.extensions

import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.drawOutline
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp


fun Modifier.drawColoredShadow(
    color: Color,
    alpha: Float = 0.2f,
    borderRadius: Dp = 0.dp,
    shadowRadius: Dp = 20.dp,
    offsetY: Dp = 0.dp,
    offsetX: Dp = 0.dp
) = this.drawBehind {
    drawColoredShadow(
        color = color,
        alpha = alpha,
        borderRadius = borderRadius,
        shadowRadius = shadowRadius,
        offsetY = offsetY,
        offsetX = offsetX
    )
}

fun Modifier.drawColoredShadowByShape(
    color: Color,
    shape: Shape,
    density: Density,
    alpha: Float = 0.2f,
    shadowRadius: Dp = 20.dp,
    offsetY: Dp = 0.dp,
    offsetX: Dp = 0.dp
) = this.drawBehind {
    drawColoredShadowByShape(
        color = color,
        shape = shape,
        alpha = alpha,
        density = density,
        shadowRadius = shadowRadius,
        offsetY = offsetY,
        offsetX = offsetX
    )
}

fun DrawScope.drawColoredShadow(
    color: Color,
    alpha: Float = 0.2f,
    width: Float = this.size.width,
    height: Float = this.size.height,
    borderRadius: Dp = 0.dp,
    shadowRadius: Dp = 20.dp,
    offsetY: Dp = 0.dp,
    offsetX: Dp = 0.dp
) {
    val transparentColor = color.copy(alpha = 0f).toArgb()
    val shadowColor = color.copy(alpha = alpha).toArgb()
    this.drawIntoCanvas {
        val paint = Paint()
        val frameworkPaint = paint.asFrameworkPaint()
        frameworkPaint.color = transparentColor
        frameworkPaint.setShadowLayer(
            shadowRadius.toPx(),
            offsetX.toPx(),
            offsetY.toPx(),
            shadowColor
        )
        it.drawRoundRect(
            0f,
            0f,
            width,
            height,
            borderRadius.toPx(),
            borderRadius.toPx(),
            paint
        )
    }
}

fun DrawScope.drawColoredShadowByShape(
    color: Color,
    shape: Shape,
    density: Density,
    alpha: Float = 0.2f,
    shadowRadius: Dp = 20.dp,
    offsetY: Dp = 0.dp,
    offsetX: Dp = 0.dp
) {
    val transparentColor = color.copy(alpha = 0f).toArgb()
    val shadowColor = color.copy(alpha = alpha).toArgb()
    val paint = Paint()
    val frameworkPaint = paint.asFrameworkPaint()
    frameworkPaint.color = transparentColor
    frameworkPaint.setShadowLayer(
        shadowRadius.toPx(),
        offsetX.toPx(),
        offsetY.toPx(),
        shadowColor
    )
    this.drawIntoCanvas {
        it.drawOutline(shape.createOutline(size, LayoutDirection.Ltr, density), paint)
    }
}