package ar.edu.itba.ss.command

import ar.edu.itba.ss.generator.BoidGenerator
import ar.edu.itba.ss.generator.IdProvider
import ar.edu.itba.ss.generator.PredatorGenerator
import ar.edu.itba.ss.model.Universe
import ar.edu.itba.ss.io.UniverseExporter
import ar.edu.itba.ss.model.Boundaries
import ar.edu.itba.ss.model.UniverseMetadata
import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.parameters.options.default
import com.github.ajalt.clikt.parameters.options.option
import com.github.ajalt.clikt.parameters.types.double
import com.github.ajalt.clikt.parameters.types.int

class Generate : CliktCommand(help = "Generate a universe") {

    private val boidsDefault = 500
    private val boids: Int by option(help = "Amount of boids to generate. Default $boidsDefault").int().default(boidsDefault)

    private val boidSpeedDefault = 5.0
    private val boidSpeed: Double by option(help = "Initial boid speed. Default '$boidSpeedDefault'").double().default(boidSpeedDefault)

    private val predatorsDefault = 5
    private val predators: Int by option(help = "Amount of predators to generate. Default $predatorsDefault").int().default(predatorsDefault)

    private val predatorSpeedDefault = 1.0
    private val predatorSpeed: Double by option(help = "Initial predator speed. Default '$predatorSpeedDefault'").double().default(predatorSpeedDefault)

    private val widthDefault = 20.0
    private val width: Double by option(help = "Width of the simulation area. Default $widthDefault").double().default(widthDefault)

    private val heightDefault = 20.0
    private val height: Double by option(help = "Height of the simulation area. Default $heightDefault").double().default(heightDefault)

    private val depthDefault = 20.0
    private val depth: Double by option(help = "Depth of the simulation area. Default $depthDefault").double().default(depthDefault)

    private val outputPathDefault = "universe.xyz"
    private val outputPath: String by option(help = "Where the output file will be written to. Default '$outputPathDefault'").default(outputPathDefault)

    override fun run() {
        val idProvider = IdProvider()
        val boidsGenerator = BoidGenerator(idProvider, boids, boidSpeed, width, height, depth)
        val predatorGenerator = PredatorGenerator(idProvider, predators, predatorSpeed, width, height, depth)
        val builder = Universe.Builder(UniverseMetadata.Builder(Boundaries(width, height, depth)))
        builder.entities = boidsGenerator.generate() + predatorGenerator.generate()
        UniverseExporter(outputPath).use {
            it.write(builder.build())
        }
    }

}
