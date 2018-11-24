package ar.edu.itba.ss

import ar.edu.itba.ss.command.Generate
import ar.edu.itba.ss.command.Simulate
import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.core.subcommands

class Boids : CliktCommand(help = "Boids simulator and analyzer") {
    override fun run() = Unit
}

fun main(args: Array<String>) = Boids().subcommands(
    Generate(), Simulate()
).main(args)
