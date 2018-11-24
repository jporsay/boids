package ar.edu.itba.ss.grid

import ar.edu.itba.ss.model.Entity

class Cell {
    private val entities: ArrayList<Entity> = arrayListOf()
    private val neighbours: ArrayList<Cell> = arrayListOf()

    fun addNeighbour(cell: Cell) {
        neighbours.add(cell)
    }

    fun add(entity: Entity) {
        entities.add(entity)
    }

    fun getNear(entity: Entity, distance: Double): List<Entity> {
        return entities.filter { it.position.distance(entity.position) <= distance }
    }
}
