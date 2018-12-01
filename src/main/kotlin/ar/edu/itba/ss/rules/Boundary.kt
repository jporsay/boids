package ar.edu.itba.ss.rules

import ar.edu.itba.ss.model.Entity
import ar.edu.itba.ss.model.Type
import ar.edu.itba.ss.model.Universe
import org.apache.commons.math3.geometry.euclidean.threed.Vector3D

class Boundary(factor: Double): Rule(factor) {

    override fun appliesTo(type: Type): Boolean = true

    override fun doApply(entity: Entity, neighbours: List<Entity>, universe: Universe): Vector3D {
        var vx = 0.0
        if (entity.position.x < universe.metadata.boundaries.xMin) {
            vx = entity.type.maxSpeed
        } else if (entity.position.x > universe.metadata.boundaries.xMax) {
            vx = -entity.type.maxSpeed
        }

        var vy = 0.0
        if (entity.position.y < universe.metadata.boundaries.yMin) {
            vy = entity.type.maxSpeed
        } else if (entity.position.y > universe.metadata.boundaries.yMax) {
            vy = -entity.type.maxSpeed
        }

        var vz = 0.0
        if (entity.position.z < universe.metadata.boundaries.zMin) {
            vz = entity.type.maxSpeed
        } else if (entity.position.z > universe.metadata.boundaries.zMax) {
            vz = -entity.type.maxSpeed
        }

        return Vector3D(vx, vy, vz)
    }
}
