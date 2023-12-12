package com.alterjuice.compose_utils.ui.components.charts

import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import com.patrykandpatrick.vico.compose.component.shape.shader.BrushShader
import com.patrykandpatrick.vico.core.chart.DefaultPointConnector
import com.patrykandpatrick.vico.core.chart.line.LineChart

fun defaultLineSpec(color: Color) = LineChart.LineSpec(
    lineBackgroundShader = BrushShader(
        brush = Brush.verticalGradient(
            colors= listOf(color.copy(alpha = 0.5f), Color.Transparent)
        )
    ),
    lineColor = color.toArgb(),
    pointConnector = DefaultPointConnector(cubicStrength = 0.5f),
)