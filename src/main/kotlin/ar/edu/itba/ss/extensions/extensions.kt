package ar.edu.itba.ss.extensions

import org.apache.commons.math3.geometry.euclidean.threed.Vector3D

fun Vector3D.toXYZ(): String {
    return "$x\t$y\t$z"
}

operator fun Vector3D.times(value: Double): Vector3D = this.scalarMultiply(value)
operator fun Vector3D.div(value: Double): Vector3D = this.scalarMultiply(1 / value)
operator fun Vector3D.div(value: Int): Vector3D = this.scalarMultiply(1.0 / value)

operator fun Vector3D.plus(other: Vector3D): Vector3D = this.add(other)

