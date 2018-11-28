package ar.edu.itba.ss.rules

import ar.edu.itba.ss.extensions.div
import ar.edu.itba.ss.extensions.plus
import ar.edu.itba.ss.model.Entity
import ar.edu.itba.ss.model.Type
import ar.edu.itba.ss.model.Universe
import org.apache.commons.math3.geometry.euclidean.threed.Vector3D

class Cohesion(private val factor: Double) : FlockRule() {

    override fun appliesTo(type: Type): Boolean = type == Type.Boid

    override fun doApply(entity: Entity, neighbors: List<Entity>, universe: Universe): Vector3D {
        val neighbours = filterNeighbours(entity, neighbors)
        if (neighbours.isEmpty()) return Vector3D.ZERO

        val centerOfMass = neighbours.map { it.position }.reduce { acc, vector3D -> acc + vector3D } / neighbours.size
        return centerOfMass / factor
    }
}
