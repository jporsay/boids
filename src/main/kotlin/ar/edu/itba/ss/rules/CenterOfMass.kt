package ar.edu.itba.ss.rules

import ar.edu.itba.ss.extensions.div
import ar.edu.itba.ss.extensions.plus
import ar.edu.itba.ss.model.Entity
import ar.edu.itba.ss.model.Type
import ar.edu.itba.ss.model.Universe
import org.apache.commons.math3.geometry.euclidean.threed.Vector3D

class CenterOfMass(private val factor: Double) : Rule() {

    override fun appliesTo(type: Type): Boolean = type == Type.Boid

    override fun doApply(entity: Entity, universe: Universe): Vector3D {

        val neighbours = universe.getNear(entity).filter { entity.sees(it) }
        if (neighbours.isEmpty()) return Vector3D.ZERO



        val centerOfMass = neighbours.map { it.position }.reduce { acc, vector3D -> acc + vector3D } / neighbours.size
        return centerOfMass / factor
    }
}
