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

    fun toXYZ(ss: StringBuilder): StringBuilder {
        ss.appendln(entities.size)
        metadata.toXYZ(ss)
        entities.forEach {
            it.toXYZ(ss)
        }
        return ss
    }

    class Builder(var metadata : UniverseMetadata.Builder) {
        var entities: List<Entity> = ArrayList()

        constructor(from: Universe): this(UniverseMetadata.Builder(from.metadata)) {
            entities = from.entities
        }
        fun build(): Universe = Universe(
            metadata.build(), entities
        )
    }
}
