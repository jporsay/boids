package ar.edu.itba.ss.utils

import org.apache.commons.math3.geometry.euclidean.threed.Vector3D
import kotlin.math.abs
import kotlin.math.sqrt

fun angle(v1: Vector3D, v2: Vector3D) : Double {
    val mag2 = v1.x * v1.x + v1.y * v1.y + v1.z * v1.z
    val vmag2 = v2.x * v2.x + v2.y * v2.y + v2.z * v2.z
    val dot = v1.x * v2.x + v1.y * v2.y + v1.z * v2.z
    return acos(dot / sqrt(mag2 * vmag2))
}

private fun acos(x: Double): Double {
    val negate = if (x < 0) 1.0 else 0.0
    val xabs = abs(x)
    var ret = -0.0187293
    ret *= xabs
    ret += 0.0742610
    ret *= xabs
    ret -= 0.2121144
    ret *= xabs
    ret += 1.5707288
    ret *= sqrt(1.0 - xabs)
    ret -= 2 * negate * ret
    return negate * 3.14159265358979 + ret
}
