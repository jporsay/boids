package ar.edu.itba.ss.io

import ar.edu.itba.ss.model.Entity
import ar.edu.itba.ss.model.Universe
import ar.edu.itba.ss.model.UniverseMetadata
import java.io.BufferedReader
import java.io.EOFException
import java.io.FileReader

class UniverseImporter(inputPath: String) : Iterator<Universe> {

    private val buffer = BufferedReader(FileReader(inputPath))

    override fun hasNext(): Boolean = buffer.ready()

    override fun next(): Universe {
        val builder = Universe.Builder()
        var entityCount = parseInt(nextLine(), "Invalid particle count. Got %s")
        builder.metadata = UniverseMetadata.Builder.fromXYZ(nextLine())
        var entitiesParsed = 0
        val entities = ArrayList<Entity>(entityCount)
        while (entitiesParsed < entityCount) {
            val entity = Entity.Builder.fromXYZ(nextLine()).build()
            if (entity.id >= 0) {
                entitiesParsed++
                entities.add(entity)
            } else {
                entityCount--
            }
        }
        builder.entities = entities
        return builder.build()
    }

    private fun nextLine(): String {
        return buffer.readLine() ?: throw EOFException()
    }

    private fun parseInt(line: String, errorMessage: String): Int {
        try {
            return line.trim().toInt()
        } catch (e: NumberFormatException) {
            throw IllegalStateException(errorMessage)
        }
    }
}
