package ar.edu.itba.ss.rules

import ar.edu.itba.ss.extensions.div
import ar.edu.itba.ss.extensions.minus
import ar.edu.itba.ss.extensions.plus
import ar.edu.itba.ss.model.Entity
import ar.edu.itba.ss.model.Universe
import org.apache.commons.math3.geometry.euclidean.threed.Vector3D

class Cohesion(private val factor: Double) : FlockRule() {

    override fun applyToBoid(entity: Entity, boidsAtSight: List<Entity>, universe: Universe): Vector3D {
        return boidsAtSight
            .fold(Vector3D.ZERO) { acc, boid -> acc + boid.position}
            .div(boidsAtSight.size)
            .minus(entity.position)
            .scalarMultiply(factor)
    }
}
