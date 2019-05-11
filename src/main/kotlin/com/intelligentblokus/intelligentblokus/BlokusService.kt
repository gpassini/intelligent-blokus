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

    /**
     * Throws [NoMoveLeftException] if there are no available moves.
     */
    @Deprecated("This function will disappear since the board should not keep track of whose turn it is.", level = DeprecationLevel.WARNING)
    fun getAvailableMoves(availablePieces: List<BlokusPiece>): List<BlokusMove> {
        val playerEnum = board.getNextPlayer()
        return availablePieces
                .flatMap { it.getVariations() }
                .flatMap { this.getPieceVariationMoves(it, playerEnum) }
                .ifEmpty { throw NoMoveLeftException(playerEnum) }
    }

    fun passTurn() {
        board.passTurn()
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
