package ar.edu.itba.ss.model

import org.apache.commons.math3.geometry.euclidean.threed.Vector3D

data class Boid(val id: Int, val position: Vector3D, val velocity: Vector3D)
