package ar.edu.itba.ss.model
import ar.edu.itba.ss.grid.Grid
import java.lang.StringBuilder

class Universe(
    val metadata: UniverseMetadata,
    val entities: List<Entity>
) {
    private val grid: Grid = Grid.newGrid(metadata, entities = entities)

    fun getNear(entity: Entity): List<Entity> {
        return grid.cellFor(entity).getNear(entity, metadata.interactionDistance)
    }

    fun getNear(entity: Entity, searchDistance: Double): List<Entity> {
        return grid.cellFor(entity).getNear(entity, searchDistance)
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
