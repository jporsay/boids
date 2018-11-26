package ar.edu.itba.ss.rules

import ar.edu.itba.ss.model.Entity
import ar.edu.itba.ss.model.Type
import ar.edu.itba.ss.model.Universe
import org.apache.commons.math3.geometry.euclidean.threed.Vector3D

class Boundary(private val boundaryMargin: Double = 0.05, private val speed: Double = 2.0): Rule() {

    override fun appliesTo(type: Type): Boolean = type == Type.Boid

    override fun doApply(entity: Entity, universe: Universe): Vector3D {
        val minBoundaryMargin = boundaryMargin
        val maxBoundaryMargin = 1.0 - minBoundaryMargin
        val position = entity.position
        var vy = 0.0
        if (position.y < universe.metadata.height * minBoundaryMargin) {
            vy = speed
        } else if (position.y > universe.metadata.height * maxBoundaryMargin) {
            vy = -speed
        }

        if (universe.metadata.loopContour) return Vector3D(0.0, vy, 0.0)

        var vx = 0.0
        var vz = 0.0

        if (position.x < universe.metadata.width * minBoundaryMargin) {
            vx = speed
        } else if (position.x > universe.metadata.width * maxBoundaryMargin) {
            vx = -speed
        }

        if (position.z < universe.metadata.depth * minBoundaryMargin) {
            vz = speed
        } else if (position.z > universe.metadata.depth * maxBoundaryMargin) {
            vz = -speed
        }

        return Vector3D(vx, vy, vz)
    }
}
