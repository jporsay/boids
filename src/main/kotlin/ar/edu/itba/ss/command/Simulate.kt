package ar.edu.itba.ss.command

import ar.edu.itba.ss.io.UniverseImporter
import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.parameters.options.default
import com.github.ajalt.clikt.parameters.options.flag
import com.github.ajalt.clikt.parameters.options.option
import com.github.ajalt.clikt.parameters.types.int

class Simulate : CliktCommand(help = "Simulate a given universe") {

    private val secondsDefault = 500
    private val seconds: Int by option(help = "Seconds that we should simulate. Default $secondsDefault").int().default(secondsDefault)

    private val loop: Boolean by option(help = "Loop contours").flag()

    private val inputPathDefault = "universe.xyz"
    private val inputPath: String by option(help = "Simulation input file. Default '$inputPathDefault'").default(inputPathDefault)

    private val outputPathDefault = "universe.sim.xyz"
    private val outputPath: String by option(help = "Simulation output file. Default '$outputPathDefault'").default(outputPathDefault)

    override fun run() {
        val universe = UniverseImporter(inputPath).next()

    }
}
