package ar.edu.itba.ss.grid

import ar.edu.itba.ss.model.Boid

class Cell(val x: Int, val y: Int, val z: Int) {
    private val entities: ArrayList<Boid> = arrayListOf()
    private val neighbours: ArrayList<Cell> = arrayListOf()

    fun addNeighbour(cell: Cell) {
        neighbours.add(cell)
    }
}
