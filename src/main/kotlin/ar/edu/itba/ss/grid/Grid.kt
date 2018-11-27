package ar.edu.itba.ss.grid;

import ar.edu.itba.ss.model.Entity
import ar.edu.itba.ss.model.UniverseMetadata
import kotlin.math.roundToInt

abstract class Grid(private val universeMetadata: UniverseMetadata, private val entities: List<Entity>) {
    protected val xCells = (universeMetadata.boundaries.xMax / universeMetadata.interactionDistance).roundToInt()
    protected val yCells = (universeMetadata.boundaries.yMax / universeMetadata.interactionDistance).roundToInt()
    protected val zCells = (universeMetadata.boundaries.zMax / universeMetadata.interactionDistance).roundToInt()
    protected var cells: Array<Array<Array<Cell>>> = Array(xCells) { Array(yCells) { Array(zCells) { Cell() } } }

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
        initEntities()
    }

    private fun initNeighbours() {
        cells.forEachIndexed { x, xArray ->
            xArray.forEachIndexed { y, yArray ->
                yArray.forEachIndexed { z, cell ->
                    neighbourCoords.forEach {
                        val xDiff = it.first.first + x
                        val yDiff = it.first.second + y
                        val zDiff = it.second + z
                        addCellNeighbour(cell, xDiff, yDiff, zDiff)
                    }
                }
            }
        }
    }

    abstract fun addCellNeighbour(cell: Cell, newX : Int, newY : Int, newZ: Int)

    private fun initEntities() {
        entities.forEach { cellFor(it).add(it) }
    }

    fun cellFor(entity: Entity): Cell {
        val x = Math.floor(entity.position.x / universeMetadata.interactionDistance).toInt()
        val y = Math.floor(entity.position.y / universeMetadata.interactionDistance).toInt()
        val z = Math.floor(entity.position.z / universeMetadata.interactionDistance).toInt()
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
