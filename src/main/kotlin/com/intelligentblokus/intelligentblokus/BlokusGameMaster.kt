package com.intelligentblokus.intelligentblokus

import com.intelligentblokus.intelligentblokus.exception.NoMoveLeftException
import com.intelligentblokus.intelligentblokus.piece.BlokusPiece
import com.intelligentblokus.intelligentblokus.play_strategy.BlokusPlayStrategy
import com.intelligentblokus.intelligentblokus.play_strategy.BlokusPlayStrategyEnum
import com.intelligentblokus.intelligentblokus.play_strategy.BlokusPlayerEnum
import com.intelligentblokus.intelligentblokus.property.BlokusProperties
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class BlokusGameMaster @Autowired constructor(private val board: BlokusBoard,
                                              private val pieces: Set<BlokusPiece>,
                                              playStrategies: List<BlokusPlayStrategy>,
                                              blokusProperties: BlokusProperties) {
    private val log = LoggerFactory.getLogger(javaClass)
    private val players: List<BlokusPlayer>
    private var turn = 0

    init {
        players = blokusProperties.players.map { playerEntry -> BlokusPlayer(playerEntry.key, getPlayStrategyBean(playStrategies, playerEntry.value), pieces.toMutableSet()) }
        log.info("Players: {}", players.joinToString(prefix = "\n", separator = "\n"))
    }

    private fun getPlayStrategyBean(playStrategies: List<BlokusPlayStrategy>, playStrategyEnum: BlokusPlayStrategyEnum) =
            playStrategies.find { it.getEnum() == playStrategyEnum }
                    ?: throw IllegalStateException("No play strategy [ $playStrategyEnum ] found.")

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
            verifyGameOver()
        }
        return board
    }

    fun isGameOver(): Boolean {
        return players.all { it.gameOver }
    }

    fun reset() {
        board.reset()
        turn = 0
        players.forEach {
            it.gameOver = false
            it.pieces.addAll(pieces.toMutableSet())
        }
    }

    private fun verifyGameOver() {
        if (isGameOver()) {
            val tilesByPlayer: Map<BlokusPlayerEnum, Int> = players.associateBy(
                    { it.playerEnum },
                    {
                        it.pieces
                                .map { piece -> piece.getTilesNumber() }
                                .reduce { acc, tiles -> acc + tiles }
                    })
            showScore(tilesByPlayer)
        }
    }

    private fun showScore(tilesByPlayer: Map<BlokusPlayerEnum, Int>) {
        tilesByPlayer.ifEmpty { throw IllegalArgumentException("The players list must not be empty.") }
        val minimumTiles = tilesByPlayer.minBy { it.value }!!.value
        val winners: Set<BlokusPlayerEnum> = tilesByPlayer.filterValues { it == minimumTiles }.keys
        log.info("Player(s) {} won!", winners.joinToString())
        log.info("Final score:")
        tilesByPlayer.forEach { (player, tilesNumber) -> log.info("{}: {}", player, tilesNumber) }
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
