package ar.edu.itba.ss.io

import ar.edu.itba.ss.model.Boundary
import ar.edu.itba.ss.model.Entity
import ar.edu.itba.ss.model.Universe
import org.apache.commons.math3.geometry.euclidean.threed.Vector3D

class OriginStartBoundaryProvider : BoundaryProvider {
    override fun getFor(universe: Universe): List<Entity> = listOf(
        Boundary(Vector3D(0.0, 0.0, 0.0)),
        Boundary(Vector3D(0.0, universe.metadata.height, 0.0)),
        Boundary(Vector3D(universe.metadata.width, 0.0, 0.0)),
        Boundary(Vector3D(universe.metadata.width, universe.metadata.height, 0.0)),
        Boundary(Vector3D(0.0, 0.0, universe.metadata.depth)),
        Boundary(Vector3D(0.0, universe.metadata.height, universe.metadata.depth)),
        Boundary(Vector3D(universe.metadata.width, 0.0, universe.metadata.depth)),
        Boundary(Vector3D(universe.metadata.width, universe.metadata.height, universe.metadata.depth))
    )
}
