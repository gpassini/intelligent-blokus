package com.intelligentblokus.intelligentblokus

import com.intelligentblokus.intelligentblokus.piece.BlokusPiece
import com.intelligentblokus.intelligentblokus.piece.impl.BlokusPiece1
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.shell.standard.ShellComponent
import org.springframework.shell.standard.ShellMethod

@ShellComponent
class BlokusShell @Autowired constructor(private val blokusService: BlokusService,
                                         @Suppress("SpringJavaInjectionPointsAutowiringInspection") private val pieces: List<BlokusPiece> ) {

    private val blokusBoard = BlokusBoard()

    @ShellMethod(value = "Chose a position (x and y, 0 to 4 each) to play the piece '1'.", key = ["p"])
    fun play(x: Int, y: Int): String {
        return blokusBoard.playMove(BlokusMove(BlokusPlayerEnum.BLACK, BlokusPiece1.getVariations()[0], x, y)).toString()
    }



    @ShellMethod(value = "Play randomly.", key = ["r"])
    fun playRandom(): String {
        val availableMoves = blokusService.getAvailableMoves(pieces)
        if (availableMoves.isEmpty()) {
            blokusService.passTurn()
            return "Game over"
        }
        return blokusBoard.playMove(availableMoves.shuffled()[0]).toString()
    }
}