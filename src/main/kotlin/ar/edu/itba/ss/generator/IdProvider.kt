package ar.edu.itba.ss.generator

class IdProvider {
    private var nextId: Int = 0

    fun next(): Int {
        return nextId++
    }
}
