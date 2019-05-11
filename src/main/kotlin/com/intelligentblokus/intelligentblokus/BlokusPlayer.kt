package com.intelligentblokus.intelligentblokus

import com.intelligentblokus.intelligentblokus.piece.BlokusPiece
import com.intelligentblokus.intelligentblokus.play_strategy.BlokusPlayStrategy
import com.intelligentblokus.intelligentblokus.play_strategy.BlokusPlayerEnum

data class BlokusPlayer(
        val playStrategy: BlokusPlayStrategy,
        val playerEnum: BlokusPlayerEnum,
        val pieces: MutableList<BlokusPiece>,
        var gameOver: Boolean = false) {
    fun play(gameState: BlokusGameState) = playStrategy.play(gameState)
}