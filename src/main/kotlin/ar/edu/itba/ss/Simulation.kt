package ar.edu.itba.ss

import ar.edu.itba.ss.extensions.*
import ar.edu.itba.ss.model.Entity
import ar.edu.itba.ss.model.Universe
import ar.edu.itba.ss.rules.Rule
import org.apache.commons.math3.geometry.euclidean.threed.Vector3D
import kotlin.streams.toList

class Simulation(
    var universe: Universe,
    private val rules: List<Rule>,
    private val dT: Double
) {

    // http://www.kfish.org/boids/pseudocode.html
    fun step(): Universe {
        val universeBuilder = Universe.Builder(universe)
        universeBuilder.entities = universe.entities.parallelStream().map { entity ->

            val neighbours = universe.getNear(entity)
            var velocity = entity.velocity
            rules.forEach {
                velocity = velocity.add(it.apply(entity, neighbours, universe))
            }
            velocity = clampVelocity(velocity, entity.type.maxSpeed)
            val position = entity.position + velocity * dT
            val builder = Entity.Builder(entity)
            builder.velocity = velocity
            builder.position = if (universe.metadata.loopContour) warpPosition(position) else position
            builder.build()
        }.toList()
        universe = universeBuilder.build()
        return universe
    }

    private fun clampVelocity(velocity: Vector3D, maxSpeed: Double): Vector3D {
        if (velocity.norm <= maxSpeed) return velocity
        return velocity.normalize() * maxSpeed
    }

    private fun warpPosition(position: Vector3D): Vector3D = Vector3D(
        warpValue(position.x, universe.metadata.boundaries.xMax),
        warpValue(position.y, universe.metadata.boundaries.yMax),
        warpValue(position.z, universe.metadata.boundaries.zMax)
    )

    // TODO: shoud use min too.
    private fun warpValue(value: Double, max: Double): Double = (value % max + max) % max
}
