package com.intelligentblokus.intelligentblokus

import com.intelligentblokus.intelligentblokus.piece.BlokusPiece
import com.intelligentblokus.intelligentblokus.play_strategy.BlokusPlayStrategy
import com.intelligentblokus.intelligentblokus.play_strategy.BlokusPlayerEnum

data class BlokusPlayer(
        val playerEnum: BlokusPlayerEnum,
        val playStrategy: BlokusPlayStrategy,
        val pieces: MutableSet<BlokusPiece>,
        var gameOver: Boolean = false) {

    fun play(gameState: BlokusGameState) = playStrategy.play(gameState)

    fun tilesNumberLeft() = pieces.map { it.getTilesNumber() }.sum()

    fun copy() = BlokusPlayer(playerEnum, playStrategy, pieces.toMutableSet(), gameOver)
}