package ar.edu.itba.ss.model

import ar.edu.itba.ss.extensions.fastAppend
import java.lang.StringBuilder

class UniverseMetadata(
    val age: Double,
    val loopContour: Boolean = true,
    val interactionDistance: Double,
    val boundaries: Boundaries
) {

    fun toXYZ(ss: StringBuilder): StringBuilder {
        ss.append("age=").fastAppend(age).append('\t')
        return boundaries.toXYZ(ss).appendln()
    }

    class Builder(var boundaries: Boundaries) {

        var age: Double = 0.0
        var loopContour: Boolean = true
        var interactionDistance: Double = 1.0

        constructor(from: UniverseMetadata): this(from.boundaries) {
            age = from.age
            loopContour = from.loopContour
            interactionDistance = from.interactionDistance
        }

        companion object {
            fun fromXYZ(line: String): UniverseMetadata.Builder {
                val kv = line.split("\t").map {
                    val sp = it.split("=")
                    sp[0] to sp[1]
                }.toMap()
                val out = UniverseMetadata.Builder(Boundaries(
                    kv["width"]!!.toDouble(),
                    kv["height"]!!.toDouble(),
                    kv["depth"]!!.toDouble()
                ))
                out.age = kv["age"]!!.toDouble()
                return out
            }
        }

        fun build(): UniverseMetadata = UniverseMetadata(
            age, loopContour, interactionDistance, boundaries
        )
    }

}

// TODO: should be zero centered. xMax = width/2 and xMin = -xMax. Idem y, z.
class Boundaries(width: Double, height: Double, depth: Double) {
    fun toXYZ(ss: StringBuilder): StringBuilder = ss
        .append("width=").fastAppend(xMax).append('\t')
        .append("height=").fastAppend(yMax).append('\t')
        .append("depth=").fastAppend(zMax)

    val xMax = width
    val xMin = 0.0
    val yMax = height
    val yMin = 0.0
    val zMax = depth
    val zMin = 0.0
}
