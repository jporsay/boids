package ar.edu.itba.ss.command

import ar.edu.itba.ss.generator.BoidGenerator
import ar.edu.itba.ss.generator.IdProvider
import ar.edu.itba.ss.generator.SpecialGenerator
import ar.edu.itba.ss.model.Universe
import ar.edu.itba.ss.io.UniverseExporter
import ar.edu.itba.ss.model.Boundaries
import ar.edu.itba.ss.model.UniverseMetadata
import ar.edu.itba.ss.utils.SeededRandom
import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.parameters.options.default
import com.github.ajalt.clikt.parameters.options.option
import com.github.ajalt.clikt.parameters.types.double
import com.github.ajalt.clikt.parameters.types.int
import kotlin.math.absoluteValue

class Generate : CliktCommand(help = "Generate a universe") {

    private val boidsDefault = 500
    private val boids: Int by option(help = "Amount of boids to generate. Default $boidsDefault").int().default(boidsDefault)

    private val specialsDefault = 0
    private val specials: Int by option(help = "Amount of special entities to generate. Default $specialsDefault").int().default(specialsDefault)

    private val widthDefault = 20.0
    private val width: Double by option(help = "Width of the simulation area. Default $widthDefault").double().default(widthDefault)

    private val heightDefault = 20.0
    private val height: Double by option(help = "Height of the simulation area. Default $heightDefault").double().default(heightDefault)

    private val depthDefault = 20.0
    private val depth: Double by option(help = "Depth of the simulation area. Default $depthDefault").double().default(depthDefault)

    private val amountDefault = 1
    private val amount: Int by option(help = "Amount of universes to generate. Default $amountDefault").int().default(amountDefault)

    override fun run() {
        (0 until amount).forEach {
            val id = (SeededRandom.randomInt().absoluteValue % 10000).toString().padStart(4, '0')
            generateUniverse(id)
        }
    }

    private fun generateUniverse(id: String) {
        val idProvider = IdProvider()
        val boidsGenerator = BoidGenerator(idProvider, boids, width, height, depth)
        val specialGenerator = SpecialGenerator(idProvider, specials, width, height, depth)
        val builder = Universe.Builder(UniverseMetadata.Builder(Boundaries(width, height, depth)))
        builder.entities = boidsGenerator.generate() + specialGenerator.generate()
        UniverseExporter("universe__id_$id.xyz").use {
            it.write(builder.build())
        }
    }

}
