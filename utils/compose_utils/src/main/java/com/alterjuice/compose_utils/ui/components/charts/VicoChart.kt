package com.alterjuice.compose_utils.ui.components.charts

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.alterjuice.utils.extensions.gracefulRound
import com.patrykandpatrick.vico.compose.axis.horizontal.rememberBottomAxis
import com.patrykandpatrick.vico.compose.axis.vertical.rememberStartAxis
import com.patrykandpatrick.vico.compose.chart.Chart
import com.patrykandpatrick.vico.compose.chart.layout.segmented
import com.patrykandpatrick.vico.compose.chart.line.lineChart
import com.patrykandpatrick.vico.compose.style.ProvideChartStyle
import com.patrykandpatrick.vico.core.axis.formatter.AxisValueFormatter
import com.patrykandpatrick.vico.core.chart.layout.HorizontalLayout
import com.patrykandpatrick.vico.core.chart.line.LineChart
import com.patrykandpatrick.vico.core.entry.ChartEntryModel
import com.patrykandpatrick.vico.core.legend.Legend

@Composable
fun VicoChart(
    modifier: Modifier,
    lines: List<LineChart.LineSpec>,
    legend: Legend? = null,
    valueLabelTransformer: (Float, ChartEntryModel) -> String,
    markerLabelTransformer: (Float, ChartEntryModel) -> String = valueLabelTransformer,
    xAxesLabelFormatter: (Int) -> String,
    chartModel: ChartEntryModel
) {
    ProvideChartStyle {
        Chart(
            modifier = modifier,
            chart = lineChart(lines),
            model = chartModel,
            startAxis = rememberStartAxis(
                valueFormatter = AxisValueFormatter { value, chartValues ->
                    valueLabelTransformer(value, chartValues.chartEntryModel)
                }
            ),
            horizontalLayout = HorizontalLayout.segmented(),
            bottomAxis = rememberBottomAxis(
                valueFormatter = AxisValueFormatter { value, chartValues ->
                    val index = value.toInt()
                    xAxesLabelFormatter(index)
                }
            ),
            isZoomEnabled = true,
            marker = rememberMarker(markerLabelTransformer),
            legend = legend
        )
    }
}