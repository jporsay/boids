package ar.edu.itba.ss.extensions

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.runBlocking
import org.apache.commons.math3.geometry.euclidean.threed.Vector3D

fun Vector3D.toXYZ(ss: StringBuilder): StringBuilder = ss
    .append(x).append('\t')
    .append(y).append('\t')
    .append(z)

operator fun Vector3D.times(value: Double): Vector3D = this.scalarMultiply(value)
operator fun Vector3D.div(value: Double): Vector3D = this.scalarMultiply(1 / value)
operator fun Vector3D.div(value: Int): Vector3D = this.scalarMultiply(1.0 / value)
operator fun Vector3D.minus(other: Vector3D): Vector3D = this.subtract(other)

operator fun Vector3D.plus(other: Vector3D): Vector3D = this.add(other)

fun <A, B>Iterable<A>.pmap(f: suspend (A) -> B): List<B> = runBlocking {
    map { async { f(it) } }.map { it.await() }
}

fun <A, B>Iterable<A>.pforEach(f: suspend (A) -> B): Unit = runBlocking(Dispatchers.Default) {
    map { async { f(it) } }.forEach { it.await() }
}
