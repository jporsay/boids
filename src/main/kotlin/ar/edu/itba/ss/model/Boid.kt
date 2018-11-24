package ar.edu.itba.ss.model

import ar.edu.itba.ss.extensions.toXYZ
import org.apache.commons.math3.geometry.euclidean.threed.Vector3D

open class Entity(val id: Int, val type: Type, val radius: Double, val position: Vector3D, val velocity: Vector3D) {
    fun toXYZ(): String {
        return "$id\t${type.ordinal}\t$radius\t${position.toXYZ()}\t${velocity.toXYZ()}"
    }
}
class Boid(id: Int, radius: Double, position: Vector3D, velocity: Vector3D) : Entity(id, Type.Boid, radius, position, velocity)
class Predator(id: Int, radius: Double, position: Vector3D, velocity: Vector3D) : Entity(id, Type.Predator, radius, position, velocity)
