package ar.edu.itba.ss.rules

import ar.edu.itba.ss.extensions.minus
import ar.edu.itba.ss.model.Entity
import ar.edu.itba.ss.model.Type
import ar.edu.itba.ss.model.Universe
import org.apache.commons.math3.geometry.euclidean.threed.Vector3D

class Separation(private val distance: Double): FlockRule() {

    override fun applyToBoid(entity: Entity, boidsAtSight: List<Entity>, universe: Universe): Vector3D {
        return boidsAtSight
            .filter { entity.position.distance(it.position) < distance }
            .fold(Vector3D.ZERO) {acc, boid -> acc.minus(boid.position.minus(entity.position))}
    }

}
