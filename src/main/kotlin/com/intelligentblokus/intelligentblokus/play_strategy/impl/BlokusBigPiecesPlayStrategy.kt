package com.intelligentblokus.intelligentblokus.play_strategy.impl

import com.intelligentblokus.intelligentblokus.BlokusGameState
import com.intelligentblokus.intelligentblokus.BlokusMove
import com.intelligentblokus.intelligentblokus.BlokusService
import com.intelligentblokus.intelligentblokus.play_strategy.AbstractBlokusPlayStrategy
import com.intelligentblokus.intelligentblokus.play_strategy.BlokusPlayStrategyEnum
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
class BlokusBigPiecesPlayStrategy @Autowired constructor(private val blokusService: BlokusService) : AbstractBlokusPlayStrategy() {

    override fun getEnum() = BlokusPlayStrategyEnum.BIG_PIECES

    override fun play(gameState: BlokusGameState): BlokusMove {
        val (playerEnum, _, pieces) = gameState.getNextPlayer()
        return blokusService.getAvailableMoves(playerEnum, pieces)
                .shuffled()
                .maxBy { it.pieceVariation.getTilesNumber() }!!
    }
}