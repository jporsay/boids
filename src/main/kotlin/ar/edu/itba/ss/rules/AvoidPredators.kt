package ar.edu.itba.ss.rules

import ar.edu.itba.ss.model.Entity
import ar.edu.itba.ss.model.Type
import ar.edu.itba.ss.model.Universe
import org.apache.commons.math3.geometry.euclidean.threed.Vector3D

class AvoidPredators(private val escapeSpeedFactor: Double): Rule() {

    override fun appliesTo(type: Type): Boolean = type == Type.Boid

    override fun doApply(entity: Entity, neighbours: List<Entity>, universe: Universe): Vector3D {
        return neighbours
            .filter { it.type == Type.Predator }
            .fold(Vector3D.ZERO) {acc, predator -> acc.add(
                predator.position
                    .subtract(entity.position)
                    .scalarMultiply(1.0/100)
            )}
            .scalarMultiply(- escapeSpeedFactor * entity.type.maxSpeed)
    }
}
