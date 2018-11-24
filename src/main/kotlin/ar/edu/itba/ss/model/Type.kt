package ar.edu.itba.ss.model

enum class Type {
    None,
    Boid,
    Predator;

    companion object {
        private val map = Type.values().associateBy(Type::ordinal)
        fun fromInt(type: Int): Type = map[type]!!
    }
}
