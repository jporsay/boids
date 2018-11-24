package ar.edu.itba.ss.utils

import org.apache.commons.math3.geometry.euclidean.threed.Vector3D

fun Vector3D.toXYZ(): String {
    return "$x\t$y\t$z"
}
