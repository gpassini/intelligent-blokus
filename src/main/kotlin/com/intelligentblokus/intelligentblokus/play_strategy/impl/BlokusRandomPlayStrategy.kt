package com.intelligentblokus.intelligentblokus.play_strategy.impl

import com.intelligentblokus.intelligentblokus.BlokusGameState
import com.intelligentblokus.intelligentblokus.BlokusMove
import com.intelligentblokus.intelligentblokus.BlokusService
import com.intelligentblokus.intelligentblokus.play_strategy.AbstractBlokusPlayStrategy
import com.intelligentblokus.intelligentblokus.play_strategy.BlokusPlayStrategyEnum
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
class BlokusRandomPlayStrategy @Autowired constructor(private val blokusService: BlokusService) : AbstractBlokusPlayStrategy() {

    override fun getEnum() = BlokusPlayStrategyEnum.RANDOM

    override fun play(gameState: BlokusGameState): BlokusMove {
        val (board, piecesByPlayer) = gameState
        val (playerEnum, pieces) = piecesByPlayer.asSequence().first()
        return blokusService.getAvailableMoves(playerEnum, pieces, board).shuffled().first()
    }
}