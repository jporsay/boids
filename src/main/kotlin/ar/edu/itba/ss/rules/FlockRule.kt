package ar.edu.itba.ss.rules

import ar.edu.itba.ss.model.Entity
import ar.edu.itba.ss.model.Type
import ar.edu.itba.ss.model.Universe
import org.apache.commons.math3.geometry.euclidean.threed.Vector3D

abstract class FlockRule : Rule() {

    final override fun appliesTo(type: Type): Boolean = type == Type.Boid

    final override fun doApply(entity: Entity, neighbours: List<Entity>, universe: Universe): Vector3D {
        val boidsAtSight = neighbours.filter { it.type == Type.Boid && entity.sees(it) }

        return if (boidsAtSight.isEmpty()) Vector3D.ZERO else applyToBoid(entity, boidsAtSight, universe)
    }

    protected abstract fun applyToBoid(entity: Entity, boidsAtSight: List<Entity>, universe: Universe): Vector3D
}
