package com.intelligentblokus.intelligentblokus

import org.springframework.shell.standard.ShellComponent
import org.springframework.shell.standard.ShellMethod

@ShellComponent
class BlokusShell {

    private val blokusBoard = BlokusBoard()

    @ShellMethod(value = "Chose a piece (1 ou 2) and a position (x and y, 0 to 4 each) to play.", key = ["p"])
    fun play(choice: Int, x: Int, y: Int): String {
        val piece = if (choice == 1) arrayOf(intArrayOf(1)) else arrayOf(intArrayOf(1, 1))
        return blokusBoard.placePieceAtPosition(piece, x, y).toString()
    }
}