package ar.edu.itba.ss.generator

import ar.edu.itba.ss.model.Type

class BoidGenerator(idProvider: IdProvider, amount: Int, universeWidth: Double, universeHeight: Double, universeDepth: Double) : EntityGenerator(
    idProvider,
    Type.Boid,
    0.1,
    amount,
    universeWidth,
    universeHeight,
    universeDepth
) {
}
