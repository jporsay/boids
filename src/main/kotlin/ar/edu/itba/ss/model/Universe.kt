package ar.edu.itba.ss.model

import ar.edu.itba.ss.grid.Grid
import java.lang.StringBuilder
import kotlin.math.roundToInt

class Universe(
    val metadata: UniverseMetadata,
    val entities: List<Entity>
) {
    val grid: Grid = Grid(
        width = (metadata.width / metadata.interactionDistance).roundToInt(),
        height = (metadata.height / metadata.interactionDistance).roundToInt(),
        depth = (metadata.depth / metadata.interactionDistance).roundToInt(),
        cellSide = metadata.interactionDistance,
        loopContour = metadata.loopContour,
        entities = entities
    )

    fun getNear(entity: Entity): List<Entity> {
        return grid.cellFor(entity).getNear(entity, metadata.interactionDistance)
    }

    fun toXYZ(): String {
        val ss = StringBuilder()
        ss.appendln(entities.size)
        ss.appendln(metadata.toXYZ())
        entities.forEach {
            ss.appendln(it.toXYZ())
        }
        return ss.toString()
    }

    class Builder() {
        var metadata: UniverseMetadata.Builder = UniverseMetadata.Builder()
        var entities: List<Entity> = ArrayList()

        constructor(from: Universe): this() {
            metadata = UniverseMetadata.Builder(from.metadata)
            entities = from.entities
        }

        fun build(): Universe = Universe(
            metadata.build(), entities
        )
    }
}
