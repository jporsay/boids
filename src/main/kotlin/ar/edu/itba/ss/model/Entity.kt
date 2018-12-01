package ar.edu.itba.ss.model

import ar.edu.itba.ss.extensions.toXYZ
import org.apache.commons.math3.geometry.euclidean.threed.Vector3D
import java.lang.StringBuilder
import kotlin.math.PI
import kotlin.math.abs
import kotlin.math.max
import kotlin.math.sqrt

open class Entity(val id: Int, val type: Type, val radius: Double, val position: Vector3D, val velocity: Vector3D) {

    fun toXYZ(ss: StringBuilder): StringBuilder {
        ss
        .append(id).append('\t')
        .append(type.ordinal).append('\t')
        .append(radius).append('\t')
        position.toXYZ(ss).append('\t')
        return velocity.toXYZ(ss).appendln()
    }

    fun sees(other: Entity): Boolean {
        /*
        TODO:
            Not sure if this works for every case.
            Added some extra checks just in case
         */
        val maxVisionAngle = PI * 0.75
        var angle = angle(velocity, other.velocity)
        if (angle < 0) angle += 2 * PI
        if (angle > 2 * PI) angle -= 2 * PI
        return (angle < maxVisionAngle) || (angle > 1.25 * PI)
    }

    private fun angle(v1: Vector3D, v2: Vector3D) : Double {
        val mag2 = v1.x * v1.x + v1.y * v1.y + v1.z * v1.z
        val vmag2 = v2.x * v2.x + v2.y * v2.y + v2.z * v2.z
        val dot = v1.x * v2.x + v1.y * v2.y + v1.z * v2.z
        return acos(dot / sqrt(mag2 * vmag2))
    }

    private fun acos(x: Double): Double {
        val negate = if (x < 0) 1.0 else 0.0
        val xabs = abs(x)
        var ret = -0.0187293
        ret *= xabs
        ret += 0.0742610
        ret *= xabs
        ret -= 0.2121144
        ret *= xabs
        ret += 1.5707288
        ret *= sqrt(1.0 - xabs)
        ret -= 2 * negate * ret
        return negate * 3.14159265358979 + ret
    }

    fun distance(entity: Entity): Double {
        return max(0.0, position.distance(entity.position) - entity.radius - radius)
    }

    class Builder() {
        var id: Int = 0
        var type: Type = Type.Boid
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
                out.radius = parts[2].toDouble()
                out.position = Vector3D(parts[3].toDouble(), parts[4].toDouble(), parts[5].toDouble())
                out.velocity = Vector3D(parts[6].toDouble(), parts[7].toDouble(), parts[8].toDouble())
                return out
            }
        }
    }

}
