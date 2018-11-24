package ar.edu.itba.ss.model

class UniverseMetadata(
    val age: Double,
    val width: Double,
    val height: Double,
    val depth: Double,
    val loopContour: Boolean = true,
    val interactionDistance: Double
) {

    fun toXYZ(): String = "age=$age\twidth=$width\theight=$height\tdepth=$depth\tloop=$loopContour\tintdis=$interactionDistance"

    class Builder() {

        var age: Double = 0.0
        var width: Double = 20.0
        var height: Double = 20.0
        var depth: Double = 20.0
        var loopContour: Boolean = true
        var interactionDistance: Double = 10.0

        constructor(from: UniverseMetadata): this() {
            age = from.age
            width = from.width
            height = from.height
            depth = from.depth
            loopContour = from.loopContour
            interactionDistance = from.interactionDistance
        }

        companion object {
            fun fromXYZ(line: String): UniverseMetadata.Builder {
                val kv = line.split("\t").map {
                    val sp = it.split("=")
                    sp[0] to sp[1]
                }.toMap()
                val out = UniverseMetadata.Builder()
                out.age = kv["age"]!!.toDouble()
                out.width = kv["width"]!!.toDouble()
                out.height = kv["height"]!!.toDouble()
                out.depth = kv["depth"]!!.toDouble()
                out.loopContour = kv["loop"]!!.toBoolean()
                out.interactionDistance = kv["intdis"]!!.toDouble()
                return out
            }
        }

        fun build(): UniverseMetadata = UniverseMetadata(
            age, width, height, depth, loopContour, interactionDistance
        )
    }

}
