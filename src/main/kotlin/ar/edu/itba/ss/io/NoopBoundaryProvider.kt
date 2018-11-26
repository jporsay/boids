package ar.edu.itba.ss.io

import ar.edu.itba.ss.model.Entity
import ar.edu.itba.ss.model.Universe

class NoopBoundaryProvider : BoundaryProvider {
    override fun getFor(universe: Universe): List<Entity> = listOf()
}
