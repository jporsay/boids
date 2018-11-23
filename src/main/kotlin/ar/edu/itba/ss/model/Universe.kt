package ar.edu.itba.ss.model

class Universe(
    val age: Double,
    val width: Double,
    val height: Double,
    val depth: Double,
    val loopContour: Boolean = true,
    val entities: List<Boid>
) {
}
