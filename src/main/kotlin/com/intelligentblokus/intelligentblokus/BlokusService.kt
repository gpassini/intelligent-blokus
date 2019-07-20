package com.intelligentblokus.intelligentblokus

import com.intelligentblokus.intelligentblokus.exception.NoMoveLeftException
import com.intelligentblokus.intelligentblokus.piece.BlokusPiece
import com.intelligentblokus.intelligentblokus.piece.BlokusPieceVariation
import com.intelligentblokus.intelligentblokus.play_strategy.BlokusPlayerEnum
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service

@Service
class BlokusService {

    private val log = LoggerFactory.getLogger(javaClass)

    /**
     * Throws [NoMoveLeftException] if there are no available moves.
     */
    fun getAvailableMoves(playerEnum: BlokusPlayerEnum, availablePieces: Collection<BlokusPiece>, board: BlokusBoard): List<BlokusMove> {
        val availableMoves = availablePieces
                .flatMap { it.getVariations() }
                .flatMap { this.getPieceVariationMoves(it, playerEnum, board) }
                .ifEmpty { throw NoMoveLeftException() }
        log.debug("${availableMoves.size} available moves for player $playerEnum with ${availablePieces.size} pieces")
        return availableMoves
    }

    private fun getPieceVariationMoves(pieceVariation: BlokusPieceVariation, playerEnum: BlokusPlayerEnum, board: BlokusBoard): List<BlokusMove> {
        val availableMoves: MutableList<BlokusMove> = mutableListOf()
        val pieceShape = pieceVariation.shape
        val xPieceSize = pieceShape.size
        val yPieceSize = pieceShape[0].size
        val xLimit = BlokusBoard.BOARD_SIZE - xPieceSize + 1
        val yLimit = BlokusBoard.BOARD_SIZE - yPieceSize + 1
        for (i in 0 until xLimit) {
            for (j in 0 until yLimit) {
                val move = BlokusMove(playerEnum, pieceVariation, i, j)
                if (board.isValidMove(move)) {
                    availableMoves.add(move)
                }
            }
        }
        return availableMoves
    }
}
