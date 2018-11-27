package ar.edu.itba.ss.command

import com.github.ajalt.clikt.core.CliktCommand

class Sources : CliktCommand() {
    override fun run() {
        println(listOf(
            "http://www.cs.toronto.edu/~dt/siggraph97-course/cwr87/",
            "http://www.kfish.org/boids/",
            "https://www.red3d.com/cwr/boids/",
            "https://cs.stanford.edu/people/eroberts/courses/soco/projects/2008-09/modeling-natural-systems/boids.html"
        ))
    }

}
