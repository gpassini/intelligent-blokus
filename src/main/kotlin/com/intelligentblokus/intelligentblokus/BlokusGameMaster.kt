package com.intelligentblokus.intelligentblokus

import com.intelligentblokus.intelligentblokus.exception.NoMoveLeftException
import com.intelligentblokus.intelligentblokus.piece.BlokusPiece
import com.intelligentblokus.intelligentblokus.play_strategy.BlokusPlayStrategy
import com.intelligentblokus.intelligentblokus.play_strategy.BlokusPlayerEnum
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class BlokusGameMaster @Autowired constructor(private val board: BlokusBoard,
                                              playStrategies: List<BlokusPlayStrategy>,
                                              @Suppress("SpringJavaInjectionPointsAutowiringInspection") pieces: List<BlokusPiece>) {
    private val log = LoggerFactory.getLogger(javaClass)
    private var turn = 0
    private val players: List<BlokusPlayer>

    init {
        if (playStrategies.isEmpty()) {
            throw IllegalStateException("At least one BlokusPlayStrategy implementation bean must be available.")
        }
        val blokusPlayerEnumsArray = BlokusPlayerEnum.values()
        players = blokusPlayerEnumsArray.mapIndexed { index, blokusPlayerEnum -> BlokusPlayer(playStrategies[index % playStrategies.size], blokusPlayerEnum, pieces.toMutableList()) }
        log.info("Players: {}", players)
    }

    public fun play(): BlokusBoard {
        val gameState = getGameState()
        try {
            val move = gameState.play()
            log.info("Move: {}", move)
            board.playMove(move)
        } catch (e: NoMoveLeftException) {
            log.info("Player {} has no moves left.", e.playerEnum)
            gameState.getNextPlayer().gameOver = true
        } finally {
            turn++
        }
        return board
    }

    private fun getPlayersOrder(): List<BlokusPlayer> {
        val playersNumber = players.size
        val nextPlayerIndex = turn % playersNumber
        val indices = nextPlayerIndex until nextPlayerIndex + playersNumber
        return indices.map { players[it % playersNumber] }
    }

    private fun getGameState(): BlokusGameState {
        return BlokusGameState(board, getPlayersOrder())
    }
}
