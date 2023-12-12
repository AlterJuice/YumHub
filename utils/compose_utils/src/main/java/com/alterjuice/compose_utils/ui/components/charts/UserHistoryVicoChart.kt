package com.alterjuice.compose_utils.ui.components.charts

import android.graphics.Typeface
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.alterjuice.compose_utils.data.model.NutrientColors
import com.alterjuice.compose_utils.data.model.UserHistoryChartModel
import com.alterjuice.compose_utils.ui.extensions.rememberLambda
import com.alterjuice.theming.theme.YumHubChartLines
import com.patrykandpatrick.vico.compose.component.shapeComponent
import com.patrykandpatrick.vico.compose.component.textComponent
import com.patrykandpatrick.vico.compose.dimensions.dimensionsOf
import com.patrykandpatrick.vico.compose.legend.horizontalLegend
import com.patrykandpatrick.vico.compose.legend.legendItem
import com.patrykandpatrick.vico.compose.style.currentChartStyle
import com.patrykandpatrick.vico.core.chart.line.LineChart
import com.patrykandpatrick.vico.core.component.shape.Shapes
import com.patrykandpatrick.vico.core.entry.ChartEntryModelProducer
import com.patrykandpatrick.vico.core.entry.entryOf


@Composable
fun ColumnScope.UserHistoryVicoChartWithTitle(
    modifier: Modifier,
    title: String,
    model: UserHistoryChartModel,
    lineColors: List<Color> = listOf()
) {
    Text(
        text = title,
        style = MaterialTheme.typography.titleLarge
    )
    UserHistoryVicoChart(
        modifier = Modifier.fillMaxWidth(),
        model = model,
        lineColors = lineColors
    )
}

@Composable
fun UserHistoryVicoChart(
    modifier: Modifier,
    model: UserHistoryChartModel,
    lineColors: List<Color> = listOf()
) {
    val lines = remember {
        lineColors.map { defaultLineSpec(it) } + getDefaultLineSpecs() /* getting default for safe work */
    }
    val legends = if (model.showLegends) {
        horizontalLegend(
            items = model.entries.mapIndexed { index, entry ->
                legendItem(
                    icon = shapeComponent(
                        shape = Shapes.pillShape,
                        color = Color(lines[index].lineColor),
                    ),
                    label = textComponent(
                        color = currentChartStyle.axis.axisLabelColor,
                        textSize = 16.sp,
                        typeface = Typeface.MONOSPACE,
                    ),
                    labelText = entry.legendLabel,
                )
            },
            iconSize = 14.dp,
            iconPadding = 10.dp,
            spacing = 8.dp,
            padding = dimensionsOf(top = 8.dp),
        )
    } else null
    val chartModel = remember(model.entries) {
        val entries = model.entries.map { entry ->
            entry.values.mapIndexed { index, it ->
                entryOf(index.toFloat(), it)
            }
        }
        ChartEntryModelProducer(entries).getModel()
    }
    val xAxes = remember(model.dates) {
        model.dates.map {
            model.dateLabelFormatter(it)
        }
    }
    val xAxesLabelFormatter = rememberLambda<(Int) -> String> {
        xAxes.getOrElse(it) { it.toString() }
    }
    VicoChart(
        modifier = modifier,
        lines = lines,
        legend = legends,
        valueLabelTransformer = model.valueLabelFormatter,
        xAxesLabelFormatter = xAxesLabelFormatter,
        chartModel = chartModel
    )
}

fun getDefaultLineSpecs() = NutrientColors.mainNutrients.map {
    defaultLineSpec(it)
}