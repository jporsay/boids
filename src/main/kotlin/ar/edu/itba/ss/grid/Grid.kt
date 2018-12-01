package ar.edu.itba.ss.grid;

import ar.edu.itba.ss.model.Entity
import ar.edu.itba.ss.model.UniverseMetadata
import kotlin.math.roundToInt

abstract class Grid(
    protected val cellSideCount: Int,
    private val xCellSide: Double,
    private val yCellSide: Double,
    private val zCellSide: Double,
    entities: List<Entity>
) {
    protected var cells: Array<Array<Array<Cell>>> = Array(cellSideCount) { Array(cellSideCount) { Array(cellSideCount) { Cell() } } }

    private val neighbourCoords = arrayOf(
        1 to 0 to 0,
        1 to 1 to 0,
        1 to -1 to 0,
        -1 to 0 to 0,
        -1 to 1 to 0,
        -1 to -1 to 0,
        0 to 1 to 0,
        0 to -1 to 0,

        1 to 0 to 1,
        1 to 1 to 1,
        1 to -1 to 1,
        -1 to 0 to 1,
        -1 to 1 to 1,
        -1 to -1 to 1,
        0 to 0 to 1,
        0 to 1 to 1,
        0 to -1 to 1,

        1 to 0 to -1,
        1 to 1 to -1,
        1 to -1 to -1,
        -1 to 0 to -1,
        -1 to 1 to -1,
        -1 to -1 to -1,
        0 to 0 to -1,
        0 to 1 to -1,
        0 to -1 to -1
    )

    init {
        initNeighbours()
        initEntities(entities)
    }

    private fun initNeighbours() {
        cells.forEachIndexed { x, xArray ->
            xArray.forEachIndexed { y, yArray ->
                yArray.forEachIndexed { z, cell ->
                    neighbourCoords.forEach {
                        val xDiff = it.first.first + x
                        val yDiff = it.first.second + y
                        val zDiff = it.second + z
                        cell.addNeighbour(this[xDiff, yDiff, zDiff])
                    }
                }
            }
        }
    }

    private fun initEntities(entities: List<Entity>) {
        entities.forEach { cellFor(it).add(it) }
    }

    fun cellFor(entity: Entity): Cell {
        val x = Math.floor(entity.position.x / xCellSide).toInt()
        val y = Math.floor(entity.position.y / yCellSide).toInt()
        val z = Math.floor(entity.position.z / zCellSide).toInt()
        return this[x, y, z]
    }

    abstract operator fun get(x: Int, y: Int, z: Int): Cell

    companion object {

        fun newGrid(universeMetadata: UniverseMetadata, entities: List<Entity>) : Grid {
            return if (universeMetadata.loopContour)
                LoopGrid(universeMetadata, entities)
            else
                ContourGrid(universeMetadata, entities)
        }
    }
}
