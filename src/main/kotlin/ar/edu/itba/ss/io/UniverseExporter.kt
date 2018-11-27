package ar.edu.itba.ss.io

import ar.edu.itba.ss.model.Universe
import java.io.*
import java.util.zip.GZIPOutputStream

class UniverseExporter(private val outputPath: String, compressed: Boolean = false, append: Boolean = false, private val flushFrequency: Int = 100) : Closeable {

    private val writer: PrintWriter by lazy {
        val outputStreamWriter = if (compressed) OutputStreamWriter(GZIPOutputStream(FileOutputStream(File("$outputPath.gz"), append))) else FileWriter(outputPath, append)
        PrintWriter(BufferedWriter(outputStreamWriter))
    }

    private val frames: MutableList<Universe> = mutableListOf()

    fun write(universe: Universe) {
        frames.add(universe)
        if (frames.size >= flushFrequency) {
            flush()
        }
    }

    private fun flush() {
        frames.forEach {
            val builder = Universe.Builder(it)
            val entities = builder.entities.toMutableList()
            builder.entities = entities
            writer.print(builder.build().toXYZ())
        }
        frames.clear()
    }

    override fun close() {
        flush()
        writer.close()
    }
}
