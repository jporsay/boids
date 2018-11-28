package ar.edu.itba.ss.model

enum class Type(val maxSpeed: Double) {
    Boid(5.0),
    Predator(7.0);

    companion object {
        private val map = Type.values().associateBy(Type::ordinal)
        fun fromInt(type: Int): Type = map[type]!!
    }
}
