package ar.edu.itba.ss.generator

import ar.edu.itba.ss.model.Type

class BoidGenerator(idProvider: IdProvider, amount: Int, speed: Double, universeWidth: Double, universeHeight: Double, universeDepth: Double) : EntityGenerator(
    idProvider,
    Type.Boid,
    0.1,
    speed,
    amount,
    universeWidth,
    universeHeight,
    universeDepth
) {
}
