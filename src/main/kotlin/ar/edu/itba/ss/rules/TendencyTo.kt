package ar.edu.itba.ss.rules

import ar.edu.itba.ss.model.Entity
import ar.edu.itba.ss.model.Type
import ar.edu.itba.ss.model.Universe
import org.apache.commons.math3.geometry.euclidean.threed.Vector3D

class TendencyTo(factor: Double, private val appliesTo: Type, private val interactsWith: Type): Rule(factor) {

    override fun appliesTo(type: Type): Boolean = type == appliesTo

    override fun doApply(entity: Entity, neighbours: List<Entity>, universe: Universe): Vector3D {
        return neighbours
            .filter { it.type == interactsWith }
            .fold(Vector3D.ZERO) {acc, typeEntity -> acc.add(
                typeEntity.position
                    .subtract(entity.position)
                    .scalarMultiply(1.0/10)
            )}
            .scalarMultiply(entity.type.maxSpeed)
    }
}
