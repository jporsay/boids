package ar.edu.itba.ss.generator

import ar.edu.itba.ss.extensions.times
import ar.edu.itba.ss.model.Entity
import ar.edu.itba.ss.model.Type
import ar.edu.itba.ss.utils.SeededRandom
import org.apache.commons.math3.geometry.euclidean.threed.Vector3D

open class EntityGenerator(val idProvider: IdProvider, val type: Type, val amount: Int, val universeWidth: Double, val universeHeight: Double, val universeDepth: Double) {

    fun generate() : List<Entity> {
        val out = ArrayList<Entity>(amount)
        repeat(amount) {
            out.add(Entity(idProvider.next(), type, genPosition(), genVelocity()))
        }
        return out
    }

    private fun genVelocity() = Vector3D(
        SeededRandom.random(-1.0, 1.0),
        SeededRandom.random(-1.0, 1.0),
        SeededRandom.random(-1.0, 1.0)
    ).normalize() * SeededRandom.random(1.0, type.maxSpeed)

    private fun genPosition() = Vector3D(
        SeededRandom.random(0.0, universeWidth),
        SeededRandom.random(0.0, universeHeight),
        SeededRandom.random(0.0, universeDepth)
    )
}
