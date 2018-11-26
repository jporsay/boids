package ar.edu.itba.ss.rules

import ar.edu.itba.ss.extensions.minus
import ar.edu.itba.ss.model.Entity
import ar.edu.itba.ss.model.Type
import ar.edu.itba.ss.model.Universe
import org.apache.commons.math3.geometry.euclidean.threed.Vector3D

class AvoidBoid(private val distance: Double): Rule() {

    override fun appliesTo(type: Type): Boolean = type == Type.Boid

    override fun doApply(entity: Entity, universe: Universe): Vector3D {
        val neighbours = universe.getNear(entity)
        if (neighbours.isEmpty()) return Vector3D.ZERO

        var out = Vector3D.ZERO
        val awkwardNeighbours = neighbours.filter { it.position.distance(entity.position) < distance }

        if (awkwardNeighbours.isEmpty()) return Vector3D.ZERO

        awkwardNeighbours.forEach {
            out -= (it.position - entity.position)
        }
        return out
    }

}
