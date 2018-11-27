package ar.edu.itba.ss.grid

import ar.edu.itba.ss.model.Entity
import ar.edu.itba.ss.model.UniverseMetadata

class LoopGrid(universeMetadata: UniverseMetadata, entities: List<Entity>): Grid(universeMetadata, entities) {

    override fun addCellNeighbour(cell: Cell, newX : Int, newY : Int, newZ: Int) {
        cell.addNeighbour(this[newX, newY, newZ])
    }

    override operator fun get(x: Int, y: Int, z: Int): Cell {
        return cells[mapAxis(x, xCells)][mapAxis(y, yCells)][mapAxis(z, zCells)]
    }

    private fun mapAxis(axis: Int, cap: Int): Int {
        val modAxis = axis % cap
        return if (modAxis >= 0) modAxis else modAxis + cap
    }

}
