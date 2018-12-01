package ar.edu.itba.ss.model

enum class Type(val maxSpeed: Double, val radius: Double) {
    Boid(32.0, 0.1),
    SpecialEntity(32.0, 1.0);

    companion object {
        private val map = Type.values().associateBy(Type::ordinal)
        fun fromInt(type: Int): Type = map[type]!!
    }
}
