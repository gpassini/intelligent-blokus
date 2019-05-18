package com.intelligentblokus.intelligentblokus.play_strategy.impl

import com.intelligentblokus.intelligentblokus.BlokusGameState
import com.intelligentblokus.intelligentblokus.BlokusMove
import com.intelligentblokus.intelligentblokus.BlokusService
import com.intelligentblokus.intelligentblokus.play_strategy.BlokusPlayStrategy
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty
import org.springframework.stereotype.Component

@Component
@ConditionalOnProperty("blokus.players.big-pieces")
class BlokusBigPiecesPlayStrategy @Autowired constructor(private val blokusService: BlokusService) : BlokusPlayStrategy {

    override fun play(gameState: BlokusGameState): BlokusMove {
        val (_, playerEnum, pieces) = gameState.getNextPlayer()
        return blokusService.getAvailableMoves(playerEnum, pieces)
                .shuffled()
                .maxBy { it.pieceVariation.getTilesNumber() }!!
    }
}