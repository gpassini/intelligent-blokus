package com.intelligentblokus.intelligentblokus.play_strategy.impl

import com.intelligentblokus.intelligentblokus.BlokusGameState
import com.intelligentblokus.intelligentblokus.BlokusMove
import com.intelligentblokus.intelligentblokus.BlokusService
import com.intelligentblokus.intelligentblokus.play_strategy.AbstractBlokusPlayStrategy
import com.intelligentblokus.intelligentblokus.play_strategy.BlokusPlayStrategyEnum
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import kotlin.math.max
import kotlin.math.min

@Component
class BlokusMinimaxPlayStrategy @Autowired constructor(private val blokusService: BlokusService) : AbstractBlokusPlayStrategy() {

    companion object {
        const val DEFAULT_DEPTH = 1
    }

    private val log = LoggerFactory.getLogger(this.javaClass)

    override fun getEnum() = BlokusPlayStrategyEnum.MINIMAX

    override fun play(gameState: BlokusGameState): BlokusMove {
        val (board, piecesByPlayer) = gameState
        val (playerEnum, pieces) = piecesByPlayer.asSequence().first()
        val availableMoves = blokusService.getAvailableMoves(playerEnum, pieces, board).sortedByDescending { it.pieceVariation.getTilesNumber() }
        val possibleStates = availableMoves.map { getNextStateFromMove(it, gameState) }
        val alphabetaScores = possibleStates.map { alphabeta(it, maximizingPlayer = false) }
        return availableMoves[alphabetaScores.indexOf(alphabetaScores.max())]
    }

    private fun alphabeta(
            gameState: BlokusGameState,
            depth: Int = DEFAULT_DEPTH,
            alpha: Int = Int.MIN_VALUE,
            beta: Int = Int.MAX_VALUE,
            maximizingPlayer: Boolean = true): Int {
        if (depth == 0 || isGameOver(gameState)) {
            return heuristicStateValue(gameState, maximizingPlayer)
        }
        if (maximizingPlayer) {
            var value = Int.MIN_VALUE
            for (nextGameState in getPossibleStates(gameState)) {
                value = max(value, alphabeta(nextGameState, depth - 1, alpha, beta, false))
                if (max(alpha, value) >= beta) {
                    break
                }
            }
            return value
        } else {
            var value = Int.MAX_VALUE
            for (nextGameState in getPossibleStates(gameState)) {
                value = min(value, alphabeta(nextGameState, depth - 1, alpha, beta, true))
                if (min(beta, value) <= alpha) {
                    break
                }
            }
            return value
        }
    }

    private fun getPossibleStates(gameState: BlokusGameState): List<BlokusGameState> {
        val (board, piecesByPlayer) = gameState
        val (playerEnum, pieces) = piecesByPlayer.asSequence().first()
        val possibleMoves = blokusService.getAvailableMoves(playerEnum, pieces, board)
        log.debug("Minimax player simulating ${possibleMoves.size} possibles moves for player $playerEnum.")
        return possibleMoves.map { getNextStateFromMove(it, gameState) }
    }

    private fun getNextStateFromMove(move: BlokusMove, currentGameState: BlokusGameState): BlokusGameState {
        val newBoard = currentGameState.board.playMove(move)
        val playerEnum = move.playerEnum
        val piece = move.pieceVariation.blokusPiece
        val currentPlayers = currentGameState.players
        val currentPieces = currentPlayers[playerEnum]
                ?: throw IllegalStateException("Player $playerEnum is not in the list.")
        val newPieces = currentPieces.toMutableList()
        newPieces.remove(piece) || throw IllegalStateException("Player $playerEnum didn't have piece $piece to be removed.")
        val newPlayers = LinkedHashMap(currentPlayers)
        newPlayers.replace(playerEnum, newPieces) != null || throw IllegalStateException("Player $playerEnum is not in the list.")
        return BlokusGameState(newBoard, newPlayers)
    }

    private fun heuristicStateValue(gameState: BlokusGameState, maximizingPlayer: Boolean): Int {
        val tilesNumbersLeft = gameState.players.asSequence().map { it.value.map { piece -> piece.getTilesNumber() }.sum() }.toList()
        val tilesLeftPlayer1 = tilesNumbersLeft[0]
        val tilesLeftPlayer2 = tilesNumbersLeft[1]
        val player1Advantage = tilesLeftPlayer2 - tilesLeftPlayer1
        return if (maximizingPlayer) player1Advantage else -player1Advantage
    }

    private fun isGameOver(gameState: BlokusGameState): Boolean {
        return gameState.players.map { blokusService.getAvailableMoves(it.key, it.value, gameState.board) }.all { it.isEmpty() }
    }
}