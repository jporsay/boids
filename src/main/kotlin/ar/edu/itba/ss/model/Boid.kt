package ar.edu.itba.ss.model

import ar.edu.itba.ss.extensions.toXYZ
import org.apache.commons.math3.geometry.euclidean.threed.Vector3D

open class Entity(val id: Int, val type: Type, val radius: Double, val position: Vector3D, val velocity: Vector3D) {

    fun toXYZ(): String {
        return "$id\t${type.ordinal}\t$radius\t${position.toXYZ()}\t${velocity.toXYZ()}"
    }

    fun sees(other: Entity): Boolean {
        val maxVisionAngle = Math.PI * 0.75
        return Vector3D.angle(velocity, other.velocity) < maxVisionAngle
    }

    class Builder() {
        var id: Int = 0
        var type: Type = Type.None
        var radius: Double = 0.0
        var position: Vector3D = Vector3D.ZERO
        var velocity: Vector3D = Vector3D.ZERO

        constructor(from: Entity): this() {
            id = from.id
            type = from.type
            radius = from.radius
            position = from.position
            velocity = from.velocity
        }

        fun build(): Entity = Entity(id, type, radius, position, velocity)

        companion object {
            fun fromXYZ(line: String): Builder {
                val parts = line.split("\t")
                val out = Builder()
                out.id = parts[0].toInt()
                out.type = Type.fromInt(parts[1].toInt())
                if (out.id >= 0 && out.type == Type.None) throw IllegalArgumentException("Error parsing line. Entity can't be none:\n\t$line")
                out.radius = parts[2].toDouble()
                out.position = Vector3D(parts[3].toDouble(), parts[4].toDouble(), parts[5].toDouble())
                out.velocity = Vector3D(parts[6].toDouble(), parts[7].toDouble(), parts[8].toDouble())
                return out
            }
        }
    }

}
class Boid(id: Int, radius: Double, position: Vector3D, velocity: Vector3D) : Entity(id, Type.Boid, radius, position, velocity)
class Predator(id: Int, radius: Double, position: Vector3D, velocity: Vector3D) : Entity(id, Type.Predator, radius, position, velocity)
class Boundary(position: Vector3D) : Entity(-1, Type.None, 0.000001, position, Vector3D.ZERO)
