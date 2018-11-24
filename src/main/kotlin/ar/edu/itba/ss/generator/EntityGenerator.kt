package ar.edu.itba.ss.generator

import ar.edu.itba.ss.model.Entity
import ar.edu.itba.ss.model.Type
import ar.edu.itba.ss.utils.SeededRandom
import org.apache.commons.math3.geometry.euclidean.threed.Vector3D

open class EntityGenerator(val idProvider: IdProvider, val type: Type, val radius: Double, val speed: Double, val amount: Int, val universeWidth: Double, val universeHeight: Double, val universeDepth: Double) {

    fun generate() : List<Entity> {
        val out = ArrayList<Entity>(amount)
        repeat(amount) {
            out.add(Entity(idProvider.next(), type, radius, genPosition(), genVelocity()))
        }
        return out
    }

    private fun genVelocity() = Vector3D(
        speed,
        Vector3D(
            SeededRandom.random(),
            SeededRandom.random(),
            SeededRandom.random()
        ).normalize()
    )

    private fun genPosition() = Vector3D(
        SeededRandom.random() * universeWidth,
        SeededRandom.random() * universeHeight,
        SeededRandom.random() * universeDepth
    )
}
