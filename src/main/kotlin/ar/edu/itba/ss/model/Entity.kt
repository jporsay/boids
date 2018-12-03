package ar.edu.itba.ss.model

import ar.edu.itba.ss.extensions.fastAppend
import ar.edu.itba.ss.extensions.toXYZ
import ar.edu.itba.ss.utils.angle
import org.apache.commons.math3.geometry.euclidean.threed.Vector3D
import kotlin.math.PI
import kotlin.math.max

class Entity(val id: Int, val type: Type, val position: Vector3D, val velocity: Vector3D) {

    fun toXYZ(ss: StringBuilder): StringBuilder {
        ss
        .append(id).append('\t')
        .append(type.ordinal).append('\t')
        .fastAppend(type.radius).append('\t')
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
        val otherVector = position.subtract(other.position).normalize()
        var angle = angle(velocity.normalize(), otherVector)
        if (angle < 0) angle += 2 * PI
        if (angle > 2 * PI) angle -= 2 * PI
        return (angle < maxVisionAngle) || (angle > 1.25 * PI)
    }

    fun distance(entity: Entity): Double {
        return max(0.0, position.distance(entity.position) - entity.type.radius - type.radius)
    }

    class Builder() {
        var id: Int = 0
        var type: Type = Type.Boid
        var position: Vector3D = Vector3D.ZERO
        var velocity: Vector3D = Vector3D.ZERO

        constructor(from: Entity): this() {
            id = from.id
            type = from.type
            position = from.position
            velocity = from.velocity
        }

        fun build(): Entity = Entity(id, type, position, velocity)

        companion object {
            fun fromXYZ(line: String): Builder {
                val parts = line.split("\t")
                val out = Builder()
                out.id = parts[0].toInt()
                out.type = Type.fromInt(parts[1].toInt())
                out.position = Vector3D(parts[3].toDouble(), parts[4].toDouble(), parts[5].toDouble())
                out.velocity = Vector3D(parts[6].toDouble(), parts[7].toDouble(), parts[8].toDouble())
                return out
            }
        }
    }

}
