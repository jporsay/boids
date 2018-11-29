package ar.edu.itba.ss

import ar.edu.itba.ss.command.Generate
import ar.edu.itba.ss.command.Polarization
import ar.edu.itba.ss.command.Simulate
import ar.edu.itba.ss.command.Sources
import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.core.subcommands

class Boids : CliktCommand(help = "Boids simulator and analyzer") {
    override fun run() = Unit
}

fun main(args: Array<String>) = Boids().subcommands(
    Generate(), Simulate(), Sources(), Polarization()
).main(args)
