package ar.edu.itba.ss.command

import ar.edu.itba.ss.Simulation
import ar.edu.itba.ss.io.UniverseExporter
import ar.edu.itba.ss.io.UniverseImporter
import ar.edu.itba.ss.rules.*
import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.parameters.options.default
import com.github.ajalt.clikt.parameters.options.flag
import com.github.ajalt.clikt.parameters.options.option
import com.github.ajalt.clikt.parameters.types.double
import com.github.ajalt.clikt.parameters.types.int
import com.github.ajalt.clikt.parameters.types.long
import me.tongfei.progressbar.ProgressBar
import java.io.File
import java.util.regex.Pattern

class Simulate : CliktCommand(help = "Simulate a given universe") {

    private val secondsDefault = 60
    private val seconds: Int by option(help = "Seconds that we should simulate. Default $secondsDefault").int().default(secondsDefault)

    private val loop: Boolean by option(help = "Loop contours").flag()

    private val fpsDefault = 60
    private val fps: Int by option(help = "Simulation frames per second. Default $fpsDefault").int().default(fpsDefault)

    private val distanceDefault = 2.0
    private val distance: Double by option(help = "Simulation frames per second. Default $distanceDefault").double().default(distanceDefault)

    private val limitDefault = 100L
    private val limit: Long by option(help = "Simulation frames per second. Default $fpsDefault").long().default(limitDefault)

    private val sourceUniversePattern = Pattern.compile("universe__id_(?<id>\\d{4})\\.xyz")

    private fun universes(path: String = "."): List<File> {
        return File(path)
            .walkTopDown()
            .filter { file -> sourceUniversePattern.matcher(file.name).matches() }
            .toList()
    }

    override fun run() {
        universes().parallelStream().limit(limit).forEach { path ->
            processUniverse(path.absolutePath)
        }
    }

    private fun processUniverse(path: String) {
        val builder = UniverseImporter(path).next()
        builder.metadata.loopContour = loop
        builder.metadata.interactionDistance = distance
        val universe = builder.build()
        val dT = 1.0 / fps
        val simulation = Simulation(universe, listOf(
            Alignment(0.4),
            Cohesion(0.01),
            Separation(1.0, 1.0),
            AvoidPredators(0.8),
            Boundary(1.8)
        ), dT)
        UniverseExporter(path.replace(".xyz", ".sim.xyz")).use { exporter ->
            val time = (1..(seconds * fps)).toList()
            exporter.write(universe)
            for (frame in ProgressBar.wrap(time, "Simulating")) {
                exporter.write(simulation.step())
            }
        }
    }
}
