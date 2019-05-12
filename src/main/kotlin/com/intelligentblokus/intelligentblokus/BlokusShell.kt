package com.intelligentblokus.intelligentblokus

import com.intelligentblokus.intelligentblokus.piece.impl.BlokusPiece1
import com.intelligentblokus.intelligentblokus.play_strategy.BlokusPlayerEnum
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.shell.standard.ShellComponent
import org.springframework.shell.standard.ShellMethod

@ShellComponent
class BlokusShell @Autowired constructor(private val blokusService: BlokusService,
                                         private val gameMaster: BlokusGameMaster) {

    @ShellMethod(value = "Chose a position (x and y, 0 to 4 each) to play the piece '1'.", key = ["p"])
    fun play(x: Int, y: Int): String {
        return blokusService.playMove(BlokusMove(BlokusPlayerEnum.BLACK, BlokusPiece1().getVariations()[0], x, y)).toString()
    }

    @ShellMethod(value = "Play a turn using the pre-programmed strategies", key = ["s"])
    fun playStrategy(): String {
        return gameMaster.play().toString()
    }
}