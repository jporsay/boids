package ar.edu.itba.ss.generator

import ar.edu.itba.ss.model.Type

class PredatorGenerator(idProvider: IdProvider, amount: Int, universeWidth: Double, universeHeight: Double, universeDepth: Double) : EntityGenerator(
    idProvider,
    Type.Predator,
    0.4,
    amount,
    universeWidth,
    universeHeight,
    universeDepth
) {
}
