package ar.edu.itba.ss.io

import ar.edu.itba.ss.model.Entity
import ar.edu.itba.ss.model.Universe

interface BoundaryProvider {
    fun getFor(universe: Universe): List<Entity>
}
