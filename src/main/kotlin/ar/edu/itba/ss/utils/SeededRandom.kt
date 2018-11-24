package ar.edu.itba.ss.utils

import java.util.*

object SeededRandom {

    private var generator = Random()

    fun setSeed(seed: Long) {
        generator = Random(seed)
    }

    fun random(): Double = generator.nextDouble()
    fun randomInt(): Int = generator.nextInt()
}
