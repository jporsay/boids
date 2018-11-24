package ar.edu.itba.ss.generator

import ar.edu.itba.ss.model.Type

class PredatorGenerator(idProvider: IdProvider, amount: Int, speed: Double, universeWidth: Double, universeHeight: Double, universeDepth: Double) : EntityGenerator(
    idProvider,
    Type.Predator,
    0.4,
    speed,
    amount,
    universeWidth,
    universeHeight,
    universeDepth
) {
}
