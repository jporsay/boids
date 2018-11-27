package ar.edu.itba.ss.grid

import ar.edu.itba.ss.model.Entity
import ar.edu.itba.ss.model.UniverseMetadata

class ContourGrid(universeMetadata: UniverseMetadata, entities: List<Entity>): Grid(universeMetadata, entities) {

    override fun addCellNeighbour(cell: Cell, newX : Int, newY : Int, newZ: Int) {
        if (isValidCoordinate(newX, newY, newZ)) {
            cell.addNeighbour(this[newX, newY, newZ])
        } else {
            cell.addNeighbour(voidCell)
        }
    }

    override operator fun get(x: Int, y: Int, z: Int): Cell {
        return if (isValidCoordinate(x, y, z)) cells[x][y][z] else voidCell
    }

    private fun isValidCoordinate(x: Int, y: Int, z: Int): Boolean {
        return !((x < 0 || x >= xCells) || (y < 0 || y >= yCells) || (z < 0 || z >= zCells))
    }

    companion object {
        private val voidCell: Cell by lazy { Cell() }
    }
}
