package com.intelligentblokus.intelligentblokus

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.shell.standard.ShellComponent
import org.springframework.shell.standard.ShellMethod

@ShellComponent
class BlokusShell @Autowired constructor(private val gameMaster: BlokusGameMaster) {

    @ShellMethod(value = "Play a turn using the pre-programmed strategies", key = ["p"])
    fun play(): String {
        return gameMaster.play().toString()
    }

    @ShellMethod(value = "Simulate an entire game", key = ["s"])
    fun simulate() {
        while (gameMaster.isGameOver().not()) {
            println(gameMaster.play())
        }
    }

    @ShellMethod(value = "Resets the game", key = ["r"])
    fun reset(): String {
        gameMaster.reset()
        return "The game was reset."
    }

}