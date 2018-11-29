package ar.edu.itba.ss.plot

import org.knowm.xchart.XYSeries

class PolarizationPlot : BasePlot() {
    override fun title(): String? = ""

    override fun xAxisLabel(): String = "Time (s)"

    override fun yAxisLabel(): String = "Polarization"

    override fun chartStyle(): XYSeries.XYSeriesRenderStyle = XYSeries.XYSeriesRenderStyle.Scatter

    fun add(points: List<Pair<Double, Double>>) {
        val x = points.map { it.first }
        val y = points.map { it.second }
        chart.addSeries("Polarization", x, y)
    }
}
