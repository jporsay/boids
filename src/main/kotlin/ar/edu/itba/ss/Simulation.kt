package ar.edu.itba.ss

import ar.edu.itba.ss.extensions.*
import ar.edu.itba.ss.model.Entity
import ar.edu.itba.ss.model.Universe
import ar.edu.itba.ss.rules.Rule

class Simulation(var universe: Universe, private val rules: List<Rule>, private val dT: Double) {

    // http://www.kfish.org/boids/pseudocode.html
    fun step(): Universe {
        val universeBuilder = Universe.Builder(universe)
        universeBuilder.entities = universe.entities.map { entity ->
            var velocity = entity.velocity
            rules.forEach {
                velocity = velocity.add(it.apply(entity, universe))
            }
            val position = entity.position + velocity * dT
            val builder = Entity.Builder(entity)
            builder.velocity = velocity
            builder.position = position
            builder.build()
        }
        universe = universeBuilder.build()
        return universe
    }
}
