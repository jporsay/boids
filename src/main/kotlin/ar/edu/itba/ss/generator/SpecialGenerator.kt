package ar.edu.itba.ss.generator

import ar.edu.itba.ss.model.Type

class SpecialGenerator(idProvider: IdProvider, amount: Int, universeWidth: Double, universeHeight: Double, universeDepth: Double) : EntityGenerator(
    idProvider,
    Type.SpecialEntity,
    amount,
    universeWidth,
    universeHeight,
    universeDepth
) {
}
