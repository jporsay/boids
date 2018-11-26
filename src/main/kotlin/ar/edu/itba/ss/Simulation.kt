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
            velocity = boundAxis(entity.position, velocity)
            val position = entity.position + velocity * dT
            val builder = Entity.Builder(entity)
            builder.velocity = velocity
            builder.position = clampPosition(position)
            builder.build()
        }
        universe = universeBuilder.build()
        return universe
    }

    private fun boundAxis(position: Vector3D, velocity: Vector3D): Vector3D {
        val minBoundaryMargin = 0.01
        val maxBoundaryMargin = 1.0 - minBoundaryMargin
        var out = velocity
        if (position.y < universe.metadata.height * minBoundaryMargin || position.y > universe.metadata.height * maxBoundaryMargin) {
            out = Vector3D(velocity.x, -velocity.y, velocity.z)
        }

        if (universe.metadata.loopContour) return out

        if (position.x < universe.metadata.width * minBoundaryMargin || position.x > universe.metadata.width * maxBoundaryMargin) {
            out = Vector3D(-velocity.x, velocity.y, velocity.z)
        }

        if (position.z < universe.metadata.depth * minBoundaryMargin || position.z > universe.metadata.depth * maxBoundaryMargin) {
            out = Vector3D(velocity.x, velocity.y, -velocity.z)
        }

        return out
    }

    private fun clampVelocity(velocity: Vector3D): Vector3D {
        if (!limitSpeed) return velocity
        if (velocity.norm <= maxSpeed) return velocity
        return velocity.normalize() * maxSpeed
    }

    private fun clampPosition(position: Vector3D): Vector3D = Vector3D(
        clampValue(position.x, universe.metadata.width),
        clampValue(position.y, universe.metadata.height),
        clampValue(position.z, universe.metadata.depth)
    )

    private fun clampValue(value: Double, max: Double): Double = (value % max + max) % max
}
