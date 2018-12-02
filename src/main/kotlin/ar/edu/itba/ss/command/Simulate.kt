package ar.edu.itba.ss.command

import ar.edu.itba.ss.Simulation
import ar.edu.itba.ss.io.UniverseExporter
import ar.edu.itba.ss.io.UniverseImporter
import ar.edu.itba.ss.model.Type
import ar.edu.itba.ss.rules.*
import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.parameters.options.default
import com.github.ajalt.clikt.parameters.options.flag
import com.github.ajalt.clikt.parameters.options.option
import com.github.ajalt.clikt.parameters.options.required
import com.github.ajalt.clikt.parameters.types.double
import com.github.ajalt.clikt.parameters.types.file
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

    private val distanceDefault = 3.0
    private val distance: Double by option(help = "Simulation frames per second. Default $distanceDefault").double().default(distanceDefault)

    private val limit: Long by option(help = "Limit to this amount of files.").long().default(Long.MAX_VALUE)

    private val peaceSuite: Boolean by option(help = "Generate the entire fullSuite").flag()

    private val caosSuite: Boolean by option(help = "Generate the entire fullSuite").flag()

    private val dir: File by option(help = "Directory to work in. Default '.'").file(exists = true, fileOkay = false).default(File("."))

    private fun universes(): List<File> {
        val sourceUniversePattern = Pattern.compile("universe__id_(?<id>\\d{4})\\.xyz")
        return dir
            .listFiles { file -> sourceUniversePattern.matcher(file.name).matches() }
            .toList()
    }

    override fun run() {
        universes().parallelStream().limit(limit).forEach { path ->
            suite().forEach { ruleSet -> processUniverse(path.absolutePath, ruleSet) }
        }
    }

    private fun suite() : List<RuleSet> {
        val result = mutableListOf<RuleSet>()
        if (peaceSuite) {
            val range = listOf(0.0, 0.2, 0.4, 0.6, 0.8, 1.0)
            result.addAll(range.map { RuleSet("a_$it", alignmentFactor = it) })
            result.addAll(range.map { RuleSet("c_$it", cohesionFactor = it) })
            result.addAll(range.map { RuleSet("s_$it", separationFactor = it) })

            //val aDetail = listOf(0.04, 0.08, 0.12, 0.16)
            //result.addAll(aDetail.map { RuleSet("a_$it", alignmentFactor = it) })
        }
        if (caosSuite) {
            val range = listOf(-1.0, -0.8, -0.6, -0.4, -0.2, 0.0, 0.2, 0.4, 0.6, 0.8, 1.0)
            result.addAll(range.map { RuleSet("t_$it", boidTendencyToSpecialFactor = it) })
        }
        if (!peaceSuite && !caosSuite) {
            result.add(RuleSet("default"))
        }
        return result
    }

    private fun processUniverse(path: String, ruleSet: RuleSet) {
        val builder = UniverseImporter(path).next()
        builder.metadata.loopContour = loop
        builder.metadata.interactionDistance = distance
        val universe = builder.build()
        val dT = 1.0 / fps
        val simulation = Simulation(universe, ruleSet.rules, dT)
        UniverseExporter(path.replace(".xyz", ".${ruleSet.name}.sim.xyz")).use { exporter ->
            val time = (1..(seconds * fps)).toList()
            exporter.write(universe)
            for (frame in ProgressBar.wrap(time, "Simulating")) {
                exporter.write(simulation.step())
            }
        }
    }

    class RuleSet(val name: String,
                       alignmentFactor: Double = 0.5,
                       cohesionFactor: Double = 0.5,
                       separationFactor: Double = 0.5,
                       boidTendencyToSpecialFactor: Double = -0.8,
                       specialTendencyToBoidFactor: Double = 0.0,
                       boundaryFactor: Double = 0.3
    ) {
        val rules = listOf(
            Alignment(alignmentFactor),
            Cohesion(cohesionFactor),
            Separation(separationFactor, 1.0),
            TendencyTo(boidTendencyToSpecialFactor, Type.Boid, Type.SpecialEntity),
            TendencyTo(specialTendencyToBoidFactor, Type.SpecialEntity, Type.Boid),
            Boundary(boundaryFactor)
        )
    }
}
