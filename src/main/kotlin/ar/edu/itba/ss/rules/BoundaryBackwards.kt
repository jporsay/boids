package ar.edu.itba.ss.rules

import ar.edu.itba.ss.model.Entity
import ar.edu.itba.ss.model.Type
import ar.edu.itba.ss.model.Universe
import org.apache.commons.math3.geometry.euclidean.threed.Vector3D
import kotlin.math.absoluteValue

class BoundaryBackwards(factor: Double): Rule(factor) {

    override fun appliesTo(type: Type): Boolean = true

    override fun doApply(entity: Entity, neighbours: List<Entity>, universe: Universe): Vector3D {
        var vx = 0.0
        if (entity.position.x < universe.metadata.boundaries.xMin) {
            vx = entity.velocity.x.absoluteValue
        } else if (entity.position.x > universe.metadata.boundaries.xMax) {
            vx = -entity.velocity.x.absoluteValue
        }

        var vy = 0.0
        if (entity.position.y < universe.metadata.boundaries.yMin) {
            vy = entity.velocity.y.absoluteValue
        } else if (entity.position.y > universe.metadata.boundaries.yMax) {
            vy = -entity.velocity.y.absoluteValue
        }

        var vz = 0.0
        if (entity.position.z < universe.metadata.boundaries.zMin) {
            vz = entity.velocity.z.absoluteValue
        } else if (entity.position.z > universe.metadata.boundaries.zMax) {
            vz = -entity.velocity.z.absoluteValue
        }

        return Vector3D(vx, vy, vz)
    }
}
