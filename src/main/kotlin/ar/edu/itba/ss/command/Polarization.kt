package ar.edu.itba.ss.command

import ar.edu.itba.ss.extensions.plus
import ar.edu.itba.ss.io.UniverseImporter
import ar.edu.itba.ss.model.Type
import ar.edu.itba.ss.model.Universe
import ar.edu.itba.ss.plot.BasePlot
import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.parameters.options.default
import com.github.ajalt.clikt.parameters.options.flag
import com.github.ajalt.clikt.parameters.options.option
import com.github.ajalt.clikt.parameters.types.file
import org.knowm.xchart.XYSeries
import java.io.File
import java.util.regex.Pattern
import java.util.stream.Collectors
import kotlin.math.ceil
import kotlin.math.pow

class Polarization : CliktCommand(help = "Plot anim polarization") {
    private val dir: File by option(help = "Directory to work in. Default '.'").file(exists = true, fileOkay = false).default(File("."))

    override fun run() {
        plot('a')
        plot('c')
        plot('s')
        plot('t')
    }

    private fun plot(label: Char, path: String = ".") {
        val pattern = Pattern.compile("universe__id_(?<id>\\d{4})\\.${label}_(?<value>-?\\d+\\.\\d+)\\.sim\\.xyz")
        val plot = PolarizationPlot()
        dir
            .listFiles { file -> pattern.matcher(file.name).matches() }
            .groupBy { file ->
                val matcher = pattern.matcher(file.name)
                matcher.find()
                matcher.group("value").toDouble()
            }
            .toSortedMap()
            .forEach { value, files ->
                var datapoints = DataPoints()
                files.forEach {
                    val builder = UniverseImporter(it.absolutePath)
                    builder.iterator().forEach {universe ->
                        val (x, y) = pointFor(universe)
                        datapoints.add(x, y)
                    }
                }
                plot.addSeries("$label=$value", datapoints)
            }
        if (!plot.isEmpty()) {
            plot.save(dir.resolve("polarization_$label").absolutePath, false)
        }
    }

    private fun pointFor(universe: Universe.Builder): Pair<Double, Double> {
        val boids = universe.entities.filter { it.type == Type.Boid }
        val globalVelocity = boids.map { it.velocity }.reduce { acc, it -> acc + it }
        val globalSpeed = boids.map { it.velocity.norm }.sum()
        return universe.metadata.age to globalVelocity.norm / globalSpeed
    }

    class PolarizationPlot : BasePlot() {
        override fun title(): String? = ""

        override fun xAxisLabel(): String = "Time (s)"

        override fun yAxisLabel(): String = "Polarization"

        override fun chartStyle(): XYSeries.XYSeriesRenderStyle = XYSeries.XYSeriesRenderStyle.Scatter

        fun isEmpty(): Boolean {
            return chart.seriesMap.isEmpty()
        }

        fun addSeries(label: String, points: DataPoints) {
            chart.addSeries(label, points.x(), points.y(), points.error())
        }
    }

    class DataPoints {
        private val points = mutableMapOf<Double, MutableList<Double>>()

        fun x(): DoubleArray {
            return points.keys.toDoubleArray()
        }

        fun add(x: Double, y: Double) {
            points.getOrPut(ceil(x)) { mutableListOf() }.add(y)
        }

        fun y(): DoubleArray {
            return points.values.map { it.average() }.toDoubleArray()
        }

        fun error(): DoubleArray {
            return points.values.map {
                val avg = it.average()
                val mid = it.stream()
                    .map { (it - avg).pow(2) }
                    .collect(Collectors.toList())
                    .sum() / it.size
                val stdY = Math.sqrt(mid)
                3 * (stdY / Math.sqrt(it.size.toDouble()))
            }.toDoubleArray()
        }
    }
}
