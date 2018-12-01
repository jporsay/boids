package ar.edu.itba.ss.rules

import ar.edu.itba.ss.model.Entity
import ar.edu.itba.ss.model.Type
import ar.edu.itba.ss.model.Universe
import org.apache.commons.math3.geometry.euclidean.threed.Vector3D

abstract class Rule(private val factor: Double) {
    fun apply(entity: Entity, neighbors: List<Entity>, universe: Universe): Vector3D {
        if (!appliesTo(entity.type)) return Vector3D.ZERO

        return doApply(entity, neighbors, universe).scalarMultiply(factor)
    }

    protected abstract fun appliesTo(type: Type): Boolean

    protected abstract fun doApply(entity: Entity, neighbours: List<Entity>, universe: Universe): Vector3D
}
