package ar.edu.itba.ss.model

import ar.edu.itba.ss.grid.Grid
import java.lang.StringBuilder
import kotlin.math.roundToInt

class Universe(
    val age: Double,
    val width: Double,
    val height: Double,
    val depth: Double,
    val loopContour: Boolean = true,
    val interactionDistance: Double,
    val entities: List<Entity>
) {
    val grid: Grid = Grid(
        width = (width / interactionDistance).roundToInt(),
        height = (height / interactionDistance).roundToInt(),
        depth = (depth / interactionDistance).roundToInt(),
        cellSide = interactionDistance,
        loopContour = loopContour,
        entities = entities
    )

    fun getNear(entity: Entity): List<Entity> {
        return grid.cellFor(entity).getNear(entity, interactionDistance)
    }

    fun toXYZ(): String {
        val ss = StringBuilder()
        ss.appendln(entities.size)
        ss.appendln(buildMetadata())
        entities.forEach {
            ss.appendln(it.toXYZ())
        }
        return ss.toString()
    }

    private fun buildMetadata(): String {
        return "age=$age\twidth=$width\theight=$height\tdepth=$depth"
    }

    class Builder() {
        var age: Double = 0.0
        var width: Double = 0.0
        var height: Double = 0.0
        var depth: Double = 0.0
        var loopContour: Boolean = true
        var interactionDistance: Double = 10.0
        var entities: List<Entity> = ArrayList()

        constructor(from: Universe): this() {
            age = from.age
            width = from.width
            height = from.height
            depth = from.depth
            interactionDistance = from.interactionDistance
            entities = from.entities
        }

        fun build(): Universe = Universe(
            age, width, height, depth, loopContour, interactionDistance, entities
        )
    }
}
