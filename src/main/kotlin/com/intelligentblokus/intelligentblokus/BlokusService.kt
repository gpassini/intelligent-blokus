package com.intelligentblokus.intelligentblokus

import org.springframework.stereotype.Service

@Service
class BlokusService {

    fun getAvailableMoves(board: BlokusBoard, player: BlokusPlayer, availablePieces: List<BlokusPiece>): List<BlokusMove> {
        return availablePieces.flatMap { it.getVariations() }.flatMap { this.getPieceVariationMoves(it, board, player) }
    }

    private fun getPieceVariationMoves(pieceVariation: BlokusPieceVariation, board: BlokusBoard, player: BlokusPlayer): List<BlokusMove> {
        val availableMoves: MutableList<BlokusMove> = mutableListOf()
        val pieceShape = pieceVariation.shape
        val xPieceSize = pieceShape.size
        val yPieceSize = pieceShape[0].size
        val xLimit = BlokusBoard.BOARD_SIZE - xPieceSize + 1
        val yLimit = BlokusBoard.BOARD_SIZE - yPieceSize + 1
        for (i in 0 until xLimit) {
            for (j in 0 until yLimit) {
                val move = BlokusMove(player, pieceVariation, i, j)
                if (board.isValidMove(move)) {
                    availableMoves.add(move)
                }
            }
        }
        return availableMoves
    }
}