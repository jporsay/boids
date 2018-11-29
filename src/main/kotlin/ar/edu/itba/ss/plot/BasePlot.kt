package ar.edu.itba.ss.plot

import mu.KotlinLogging
import org.knowm.xchart.*
import org.knowm.xchart.style.Styler
import java.awt.Font

abstract class BasePlot {

    private val logger = KotlinLogging.logger {}

    protected val chart: XYChart by lazy {
        val builder = XYChartBuilder()
            .width(800)
            .height(600)
            .xAxisTitle(xAxisLabel())
            .yAxisTitle(yAxisLabel())
            .theme(theme())
        if (title() != null) {
            builder.title(title()!!)
        }
        val chart = builder.build()
        chart.styler.chartTitleFont = Font(Font.MONOSPACED, Font.BOLD, 24)
        chart.styler.axisTitleFont = Font(Font.MONOSPACED, Font.BOLD, 24)
        chart.styler.axisTickLabelsFont = Font(Font.MONOSPACED, Font.BOLD, 18)
        chart.styler.legendFont = Font(Font.MONOSPACED, Font.PLAIN, 18)
        chart.styler.isErrorBarsColorSeriesColor = true
        chart.styler.defaultSeriesRenderStyle = chartStyle()
        chart.styler.legendPosition = legendPosition()
        chart.styler.isLegendVisible = showLegend()
        chart
    }

    protected abstract fun title(): String?
    protected abstract fun xAxisLabel(): String
    protected abstract fun yAxisLabel(): String
    protected abstract fun chartStyle(): XYSeries.XYSeriesRenderStyle
    protected open fun theme(): Styler.ChartTheme = Styler.ChartTheme.XChart
    protected open fun legendPosition(): Styler.LegendPosition = Styler.LegendPosition.OutsideE
    protected open fun showLegend(): Boolean = true

    fun save(fileName: String, asVectorGraphic: Boolean = true) {
        logger.info {"Saving $fileName"}
        if (asVectorGraphic) {
            VectorGraphicsEncoder.saveVectorGraphic(chart, fileName, VectorGraphicsEncoder.VectorGraphicsFormat.EPS)
        } else {
            BitmapEncoder.saveBitmapWithDPI(chart, fileName, BitmapEncoder.BitmapFormat.PNG, 300);
        }
    }

    fun show() {
        SwingWrapper(chart).displayChart()
    }
}
