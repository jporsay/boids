package ar.edu.itba.ss.extensions

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.runBlocking
import org.apache.commons.math3.geometry.euclidean.threed.Vector3D

fun Vector3D.toXYZ(sb: StringBuilder): StringBuilder = sb
    .fastAppend(x).append('\t')
    .fastAppend(y).append('\t')
    .fastAppend(z)

private val POW10 = intArrayOf(1, 10, 100, 1000, 10000, 100000, 1000000)

fun StringBuilder.fastAppend(value: Double, precision: Int = 6): StringBuilder {
    var d = value
    if (d < 0) {
        append('-')
        d = -d
    }
    val exp = POW10[precision]
    val lval = (d * exp + 0.5).toLong()
    append(lval / exp).append('.')
    val fval = lval % exp
    var p = precision - 1
    while (p > 0 && fval < POW10[p]) {
        append('0')
        p--
    }
    append(fval)
    return this
}

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

infix fun ClosedRange<Double>.step(step: Double): Iterable<Double> {
    require(start.isFinite())
    require(endInclusive.isFinite())
    require(step > 0.0) { "Step must be positive, was: $step." }
    val sequence = generateSequence(start) { previous ->
        if (previous == Double.POSITIVE_INFINITY) return@generateSequence null
        val next = previous + step
        if (next > endInclusive) null else next
    }
    return sequence.asIterable()
}
