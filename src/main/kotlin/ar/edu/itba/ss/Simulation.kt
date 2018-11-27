package ar.edu.itba.ss

import ar.edu.itba.ss.extensions.*
import ar.edu.itba.ss.model.Entity
import ar.edu.itba.ss.model.Universe
import ar.edu.itba.ss.rules.Rule
import org.apache.commons.math3.geometry.euclidean.threed.Vector3D

class Simulation(
    var universe: Universe,
    private val rules: List<Rule>,
    private val dT: Double,
    val limitSpeed: Boolean,
    val maxSpeed: Double
) {

    // http://www.kfish.org/boids/pseudocode.html
    fun step(): Universe {
        val universeBuilder = Universe.Builder(universe)
        universeBuilder.entities = universe.entities.map { entity ->
            var velocity = entity.velocity
            rules.forEach {
                velocity = velocity.add(it.apply(entity, universe))
            }
            velocity = clampVelocity(velocity)
            val position = entity.position + velocity * dT
            val builder = Entity.Builder(entity)
            builder.velocity = velocity
            builder.position = if (universe.metadata.loopContour) warpPosition(position) else position
            builder.build()
        }
        universe = universeBuilder.build()
        return universe
    }

    private fun clampVelocity(velocity: Vector3D): Vector3D {
        if (!limitSpeed) return velocity
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
