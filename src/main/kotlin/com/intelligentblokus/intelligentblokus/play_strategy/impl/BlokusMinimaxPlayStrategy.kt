package com.intelligentblokus.intelligentblokus.play_strategy.impl

import com.intelligentblokus.intelligentblokus.BlokusGameState
import com.intelligentblokus.intelligentblokus.BlokusMove
import com.intelligentblokus.intelligentblokus.BlokusPlayerState
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
        val (board, playersState) = gameState
        val (playerEnum, pieces) = playersState.first
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
        val possibleStates = getPossibleStates(gameState).sortedByDescending { heuristicStateValue(it, maximizingPlayer.not()) }
        if (maximizingPlayer) {
            var value = Int.MIN_VALUE
            var newAlpha = alpha
            for (nextGameState in possibleStates) {
                value = max(value, alphabeta(nextGameState, depth - 1, newAlpha, beta, false))
                newAlpha = max(newAlpha, value)
                if (newAlpha >= beta) {
                    break
                }
            }
            return value
        } else {
            var value = Int.MAX_VALUE
            var newBeta = beta
            for (nextGameState in possibleStates) {
                value = min(value, alphabeta(nextGameState, depth - 1, alpha, newBeta, true))
                newBeta = min(newBeta, value)
                if (newBeta <= alpha) {
                    break
                }
            }
            return value
        }
    }

    private fun getPossibleStates(gameState: BlokusGameState): List<BlokusGameState> {
        val (board, playersState) = gameState
        val (playerEnum, pieces) = playersState.first
        val possibleMoves = blokusService.getAvailableMoves(playerEnum, pieces, board)
        log.debug("Minimax player simulating ${possibleMoves.size} possibles moves for player $playerEnum.")
        return possibleMoves.map { getNextStateFromMove(it, gameState) }
    }

    private fun getNextStateFromMove(move: BlokusMove, currentGameState: BlokusGameState): BlokusGameState {
        val newBoard = currentGameState.board.playMove(move)
        val playerEnum = move.playerEnum
        val piece = move.pieceVariation.blokusPiece
        val currentPlayers = currentGameState.players
        val currentPlayerPieces = currentPlayers.first.pieces
        val newPieces = currentPlayerPieces.toMutableList()
        newPieces.remove(piece) || throw IllegalStateException("Player $playerEnum didn't have piece $piece to be removed.")
        val newPlayerState = BlokusPlayerState(currentPlayers.first.playerEnum, newPieces.toSet())
        return BlokusGameState(newBoard, Pair(currentPlayers.second, newPlayerState))
    }

    private fun heuristicStateValue(gameState: BlokusGameState, maximizingPlayer: Boolean): Int {
        val tilesLeftPlayer1 = gameState.players.first.pieces.map { piece -> piece.getTilesNumber() }.sum()
        val tilesLeftPlayer2 = gameState.players.second.pieces.map { piece -> piece.getTilesNumber() }.sum()
        val player1Advantage = tilesLeftPlayer2 - tilesLeftPlayer1
        return if (maximizingPlayer) player1Advantage else -player1Advantage
    }

    private fun isGameOver(gameState: BlokusGameState): Boolean {
        val (playerEnum1, pieces1) = gameState.players.first
        val (playerEnum2, pieces2) = gameState.players.second
        return blokusService.getAvailableMoves(playerEnum1, pieces1, gameState.board).isEmpty() &&
                blokusService.getAvailableMoves(playerEnum2, pieces2, gameState.board).isEmpty()
    }
}