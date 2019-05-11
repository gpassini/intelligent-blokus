package com.intelligentblokus.intelligentblokus

import com.intelligentblokus.intelligentblokus.exception.NoMoveLeftException
import com.intelligentblokus.intelligentblokus.piece.BlokusPiece
import com.intelligentblokus.intelligentblokus.piece.BlokusPieceVariation
import com.intelligentblokus.intelligentblokus.play_strategy.BlokusPlayerEnum
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class BlokusService @Autowired constructor(private val board: BlokusBoard) {

    /**
     * Throws [NoMoveLeftException] if there are no available moves.
     */
    fun getAvailableMoves(playerEnum: BlokusPlayerEnum, availablePieces: List<BlokusPiece>): List<BlokusMove> {
        return availablePieces
                .flatMap { it.getVariations() }
                .flatMap { this.getPieceVariationMoves(it, playerEnum) }
                .ifEmpty { throw NoMoveLeftException(playerEnum) }
    }

    private fun getPieceVariationMoves(pieceVariation: BlokusPieceVariation, playerEnum: BlokusPlayerEnum): List<BlokusMove> {
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

    fun playMove(move: BlokusMove): BlokusBoard {
        return board.playMove(move)
    }
}
