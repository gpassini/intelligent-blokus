package com.intelligentblokus.intelligentblokus

import org.springframework.stereotype.Service

@Service
class BlokusService {

    fun getAvailableMoves(board: BlokusBoard, availablePieces: List<BlokusPiece>): List<BlokusMove> {
        val player = board.getNextPlayer()
        return availablePieces.flatMap { it.getVariations() }.flatMap { this.getPieceVariationMoves(it, board, player) }
    }

    fun passTurn(board: BlokusBoard) {
        board.passTurn()
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
