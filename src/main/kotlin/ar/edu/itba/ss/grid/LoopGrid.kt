package ar.edu.itba.ss.grid

import ar.edu.itba.ss.model.Entity
import ar.edu.itba.ss.model.UniverseMetadata

class LoopGrid(universeMetadata: UniverseMetadata, entities: List<Entity>)
    : Grid(
    cellCountToDivideUniverse,
    universeMetadata.boundaries.xMax / cellCountToDivideUniverse,
    universeMetadata.boundaries.yMax / cellCountToDivideUniverse,
    universeMetadata.boundaries.zMax / cellCountToDivideUniverse,
    entities
) {

    override operator fun get(x: Int, y: Int, z: Int): Cell {
        return cells[mapAxis(x)][mapAxis(y)][mapAxis(z)]
    }

    private fun mapAxis(axis: Int): Int {
        val modAxis = axis % cellSideCount
        return if (modAxis >= 0) modAxis else modAxis + cellSideCount
    }


    companion object {
        private const val cellCountToDivideUniverse = 6
    }
}
