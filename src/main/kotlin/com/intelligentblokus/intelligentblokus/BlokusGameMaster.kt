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
class BlokusGameMaster @Autowired constructor(private val pieces: Set<BlokusPiece>,
                                              playStrategies: List<BlokusPlayStrategy>,
                                              blokusProperties: BlokusProperties) {
    private val log = LoggerFactory.getLogger(javaClass)
    private val players: Pair<BlokusPlayer, BlokusPlayer>
    private var board = BlokusBoard()
    private var turn = 0

    init {
        val playersList = blokusProperties.players.entries.map { BlokusPlayer(it.key, getPlayStrategyBean(playStrategies, it.value), pieces.toMutableSet()) }.toList()
        if (playersList.size != 2) {
            throw IllegalStateException("Exactly two players must be declared.")
        }
        players = Pair(playersList[0], playersList[1])
        log.info("Players: {}", playersList.joinToString(prefix = System.lineSeparator(), separator = System.lineSeparator()))
    }

    private fun getPlayStrategyBean(playStrategies: List<BlokusPlayStrategy>, playStrategyEnum: BlokusPlayStrategyEnum) =
            playStrategies.find { it.getEnum() == playStrategyEnum }
                    ?: throw IllegalStateException("No play strategy [ $playStrategyEnum ] found.")

    fun play(): BlokusBoard {
        val gameState = BlokusGameState(board.copy(), getPlayersOrdered())
        try {
            playTurn(gameState)
        } catch (e: NoMoveLeftException) {
            val nextPlayer = getNextPlayer()
            log.info("Player {} has no moves left.", nextPlayer.playerEnum)
            nextPlayer.gameOver = true
        } finally {
            turn++
            verifyGameOver()
        }
        return board
    }

    fun isGameOver(): Boolean {
        return players.first.gameOver && players.second.gameOver
    }

    fun reset() {
        board = BlokusBoard()
        turn = 0
        listOf(players.first, players.second).forEach {
            it.gameOver = false
            it.pieces.addAll(pieces.toMutableSet())
        }
    }

    private fun verifyGameOver() {
        if (isGameOver()) {
            val tilesByPlayer: Map<BlokusPlayerEnum, Int> = listOf(players.first, players.second).associateBy(
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

    private fun playTurn(gameState: BlokusGameState) {
        val player = getNextPlayer()
        val move = player.play(gameState)
        log.info("Move: {}", move)
        board = board.playMove(move)
        getNextPlayer().pieces.remove(move.pieceVariation.blokusPiece)
    }

    private fun getPlayersOrdered(): Pair<BlokusPlayerState, BlokusPlayerState> {
        val firstPlayer = players.first
        val firstPlayerState = BlokusPlayerState(firstPlayer.playerEnum, firstPlayer.pieces.toSet())
        val secondPlayer = players.second
        val secondPlayerState = BlokusPlayerState(secondPlayer.playerEnum, secondPlayer.pieces.toSet())
        return if (turn % 2 == 0) Pair(firstPlayerState, secondPlayerState) else Pair(secondPlayerState, firstPlayerState)
    }

    private fun getNextPlayer() = if (turn % 2 == 0) players.first else players.second
}
