package ar.edu.itba.ss.command

import ar.edu.itba.ss.Simulation
import ar.edu.itba.ss.io.UniverseExporter
import ar.edu.itba.ss.io.UniverseImporter
import ar.edu.itba.ss.rules.*
import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.parameters.options.default
import com.github.ajalt.clikt.parameters.options.flag
import com.github.ajalt.clikt.parameters.options.option
import com.github.ajalt.clikt.parameters.types.int
import me.tongfei.progressbar.ProgressBar

class Simulate : CliktCommand(help = "Simulate a given universe") {

    private val secondsDefault = 60
    private val seconds: Int by option(help = "Seconds that we should simulate. Default $secondsDefault").int().default(secondsDefault)

    private val loop: Boolean by option(help = "Loop contours").flag()

    private val fpsDefault = 60
    private val fps: Int by option(help = "Simulation frames per second. Default $fpsDefault").int().default(fpsDefault)

    private val inputPathDefault = "universe.xyz"
    private val inputPath: String by option(help = "Simulation input file. Default '$inputPathDefault'").default(inputPathDefault)

    private val outputPathDefault = "universe.sim.xyz"
    private val outputPath: String by option(help = "Simulation output file. Default '$outputPathDefault'").default(outputPathDefault)

    override fun run() {
        val builder = UniverseImporter(inputPath).next()
        builder.metadata.loopContour = loop
        builder.metadata.interactionDistance = 2.0
        val universe = builder.build()
        val dT = 1.0 / fps
        val simulation = Simulation(universe, listOf(
            Alignment(0.2),
            Cohesion(0.01),
            Separation(0.5,0.5),
            AvoidPredators(0.8),
            Boundary(0.2)
        ), dT)
        UniverseExporter(outputPath).use { exporter ->
            val time = (1..(seconds * fps)).toList()
            exporter.write(universe)
            for (frame in ProgressBar.wrap(time, "Simulating")) {
                exporter.write(simulation.step())
            }
        }
    }
}
