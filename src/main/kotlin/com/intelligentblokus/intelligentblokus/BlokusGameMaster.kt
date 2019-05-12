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
                                              pieces: List<BlokusPiece>) {
    private val log = LoggerFactory.getLogger(javaClass)
    private var turn = 0
    private val players: List<BlokusPlayer>

    init {
        if (playStrategies.isEmpty()) {
            throw IllegalStateException("At least one BlokusPlayStrategy implementation bean must be available.")
        }
        val blokusPlayerEnumsArray = BlokusPlayerEnum.values()
        players = blokusPlayerEnumsArray.mapIndexed { index, blokusPlayerEnum -> BlokusPlayer(playStrategies[index % playStrategies.size], blokusPlayerEnum, pieces.toMutableSet()) }
        log.info("Players: {}", players)
    }

    fun play(): BlokusBoard {
        val gameState = getGameState()
        try {
            playMove(gameState)
        } catch (e: NoMoveLeftException) {
            val player = gameState.getNextPlayer()
            log.info("Player {} has no moves left.", player.playerEnum)
            player.gameOver = true
        } finally {
            turn++
        }
        return board
    }

    private fun playMove(gameState: BlokusGameState) {
        val player = gameState.getNextPlayer()
        val move = player.play(gameState)
        log.info("Move: {}", move)
        board.playMove(move)
        player.pieces.remove(move.pieceVariation.blokusPiece)
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
