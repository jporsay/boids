package ar.edu.itba.ss.rules

import ar.edu.itba.ss.model.Entity
import ar.edu.itba.ss.model.Type
import ar.edu.itba.ss.model.Universe
import org.apache.commons.math3.geometry.euclidean.threed.Vector3D

class Boundary(private val escapeSpeedFactor: Double): Rule() {

    override fun appliesTo(type: Type): Boolean = true

    override fun doApply(entity: Entity, neighbours: List<Entity>, universe: Universe): Vector3D {
        val speed = escapeSpeedFactor * entity.type.maxSpeed
        var vx = 0.0
        if (entity.position.x < universe.metadata.boundaries.xMin) {
            vx = speed
        } else if (entity.position.x > universe.metadata.boundaries.xMax) {
            vx = -speed
        }

        var vy = 0.0
        if (entity.position.y < universe.metadata.boundaries.yMin) {
            vy = speed
        } else if (entity.position.y > universe.metadata.boundaries.yMax) {
            vy = -speed
        }

        var vz = 0.0
        if (entity.position.z < universe.metadata.boundaries.zMin) {
            vz = speed
        } else if (entity.position.z > universe.metadata.boundaries.zMax) {
            vz = -speed
        }

        return Vector3D(vx, vy, vz)
    }
}
