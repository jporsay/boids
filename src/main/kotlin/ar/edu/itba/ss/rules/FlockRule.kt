package ar.edu.itba.ss.rules

import ar.edu.itba.ss.model.Entity
import ar.edu.itba.ss.model.Type

abstract class FlockRule : Rule() {
    protected fun filterNeighbours(entity: Entity, neighbours: List<Entity>): List<Entity> {
        return neighbours.filter { it.type == Type.Boid && entity.sees(it) }
    }
}
