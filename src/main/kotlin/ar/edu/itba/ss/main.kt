package ar.edu.itba.ss

import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.core.subcommands

class Boids : CliktCommand(help = "Boids simulator and analyzer") {
    override fun run() = Unit
}

fun main(args: Array<String>) = Boids().subcommands(

).main(args)
