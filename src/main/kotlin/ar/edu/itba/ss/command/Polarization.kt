package ar.edu.itba.ss.command

import ar.edu.itba.ss.extensions.plus
import ar.edu.itba.ss.io.UniverseImporter
import ar.edu.itba.ss.model.Type
import ar.edu.itba.ss.model.Universe
import ar.edu.itba.ss.plot.PolarizationPlot
import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.parameters.options.default
import com.github.ajalt.clikt.parameters.options.flag
import com.github.ajalt.clikt.parameters.options.option

class Polarization : CliktCommand(help = "Plot anim polarization") {

    private val inputDefault = "universe.anim.xyz"
    private val input: String by option(help = "Simulation input file. Default '$inputDefault'").default(inputDefault)

    private val open: Boolean by option(help = "Open plot after render").flag()

    override fun run() {
        val builder = UniverseImporter(input)
        val polarizationPoints = arrayListOf<Pair<Double, Double>>()
        builder.iterator().forEach {
            val universe = it.build()
            polarizationPoints.add(pointFor(universe))
        }
        val plot = PolarizationPlot()
        plot.add(polarizationPoints)
        plot.save("polarization", false)
        if (open) {
            plot.show()
        }
    }

    private fun pointFor(universe: Universe): Pair<Double, Double> {
        val boids = universe.entities.filter { it.type == Type.Boid }
        val globalVelocity = boids.map { it.velocity }.reduce { acc, it -> acc + it }
        val globalSpeed = boids.map { it.velocity.norm }.sum()
        return universe.metadata.age to globalVelocity.norm / globalSpeed
    }
}
