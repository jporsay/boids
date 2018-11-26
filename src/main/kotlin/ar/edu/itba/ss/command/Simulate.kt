package ar.edu.itba.ss.command

import ar.edu.itba.ss.Simulation
import ar.edu.itba.ss.io.UniverseExporter
import ar.edu.itba.ss.io.UniverseImporter
import ar.edu.itba.ss.rules.AvoidBoid
import ar.edu.itba.ss.rules.CenterOfMass
import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.parameters.options.default
import com.github.ajalt.clikt.parameters.options.flag
import com.github.ajalt.clikt.parameters.options.option
import com.github.ajalt.clikt.parameters.types.double
import com.github.ajalt.clikt.parameters.types.int

class Simulate : CliktCommand(help = "Simulate a given universe") {

    private val secondsDefault = 500
    private val seconds: Int by option(help = "Seconds that we should simulate. Default $secondsDefault").int().default(secondsDefault)

    private val loop: Boolean by option(help = "Loop contours").flag()

    private val fpsDefault = 60
    private val fps: Int by option(help = "Simulation frames per second. Default $fpsDefault").int().default(fpsDefault)

    private val inputPathDefault = "universe.xyz"
    private val inputPath: String by option(help = "Simulation input file. Default '$inputPathDefault'").default(inputPathDefault)

    private val outputPathDefault = "universe.sim.xyz"
    private val outputPath: String by option(help = "Simulation output file. Default '$outputPathDefault'").default(outputPathDefault)

    private val limitSpeed: Boolean by option(help = "Enable boid speed limit").flag()

    private val maxSpeedDefault = 20.0
    private val maxSpeed: Double by option(help = "Maximum void speed. Default '$maxSpeedDefault'").double().default(maxSpeedDefault)

    override fun run() {
        val universe = UniverseImporter(inputPath).next()
        val dT = 1.0 / fps
        val simulation = Simulation(universe, listOf(
            CenterOfMass(100.0),
            AvoidBoid(1.0)
        ), dT, limitSpeed, maxSpeed)
        UniverseExporter(outputPath, boundaryProvider = OriginStartBoundaryProvider()).use { exporter ->
            exporter.write(universe)
            for (frame in 1..(seconds * fps)) {
                exporter.write(simulation.step())
            }
        }
    }
}
