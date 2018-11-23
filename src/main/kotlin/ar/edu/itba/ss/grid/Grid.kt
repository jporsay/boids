package ar.edu.itba.ss.grid

class Grid(private val width: Int, private val height: Int, private val depth: Int, private val loopContour: Boolean) {
    private var cells: Array<Array<Array<Cell>>> = Array(width) { x -> Array(height) { y -> Array(depth) { z -> Cell(x, y, z) } } }

    init {
        initNeighbours()
    }

    private fun initNeighbours() {
        val neighbourCoords = arrayOf(
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
            0 to 1 to 1,
            0 to -1 to 1,

            1 to 0 to -1,
            1 to 1 to -1,
            1 to -1 to -1,
            -1 to 0 to -1,
            -1 to 1 to -1,
            -1 to -1 to -1,
            0 to 1 to -1,
            0 to -1 to -1
        )
        cells.forEachIndexed { x, xArray ->
            xArray.forEachIndexed { y, yArray ->
                yArray.forEachIndexed { z, cell ->
                    neighbourCoords.forEach {
                        val xDiff = it.first.first + x
                        val yDiff = it.first.second + y
                        val zDiff = it.second + z
                        if (isValidCoordinate(xDiff, yDiff, zDiff)) {
                            cell.addNeighbour(this[xDiff, yDiff, zDiff])
                        }
                    }
                }
            }
        }
    }

    operator fun get(x: Int, y: Int, z: Int): Cell {
        assertValidCoordinate(x, y, z)
        return cells[mapX(x)][mapY(y)][mapZ(z)]
    }

    private fun isValidCoordinate(x: Int, y: Int, z: Int): Boolean {
        return loopContour || !((x < 0 || x >= width) || (y < 0 || y >= height) || (z < 0 || z >= depth))
    }

    private fun assertValidCoordinate(x: Int, y: Int, z: Int) {
        if (isValidCoordinate(x, y, z)) return
        throw IllegalArgumentException("Illegal coordinate: [$x, $y, $z]")
    }

    private fun mapX(x: Int): Int {
        return mapAxis(x, width)
    }

    private fun mapY(y: Int): Int {
        return mapAxis(y, height)
    }

    private fun mapZ(z: Int): Int {
        return mapAxis(z, depth)
    }

    private fun mapAxis(axis: Int, cap: Int): Int {
        if (!loopContour) {
            return axis
        }
        val modAxis = axis % cap
        return if (modAxis >= 0) modAxis else modAxis + cap
    }

}
