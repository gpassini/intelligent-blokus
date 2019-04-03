package com.intelligentblokus.intelligentblokus

/**
 * Blokus board model and utility functions.
 */
class BlokusBoard {

    /**
     * 2D matrix representing the board.
     */
    private val board: Array<IntArray> = arrayOf(
            intArrayOf(0, 0, 0, 0, 0),
            intArrayOf(0, 0, 0, 0, 0),
            intArrayOf(0, 0, 0, 0, 0),
            intArrayOf(0, 0, 0, 0, 0),
            intArrayOf(0, 0, 0, 0, 0)
    )

    /**
     * Places a given piece at a given position on the board.
     */
    fun placePieceAtPosition(piece: Array<IntArray>, x: Int, y: Int): BlokusBoard {
        val pieceYSize = piece.size
        val pieceXSize = piece[0].size
        for (j in 0 until pieceYSize) {
            for (i in 0 until pieceXSize) {
                board[j + y][i + x] += piece[j][i]
            }
        }
        return this
    }

    /**
     * Returns a representation of the this board.
     */
    override fun toString(): String {
        return "| ${board[0][0]} |  ${board[1][0]} |  ${board[2][0]} |  ${board[3][0]} |  ${board[4][0]} |\n" +
                "| ${board[0][1]} |  ${board[1][1]} |  ${board[2][1]} |  ${board[3][1]} |  ${board[4][1]} |\n" +
                "| ${board[0][2]} |  ${board[1][2]} |  ${board[2][2]} |  ${board[3][2]} |  ${board[4][2]} |\n" +
                "| ${board[0][3]} |  ${board[1][3]} |  ${board[2][3]} |  ${board[3][3]} |  ${board[4][3]} |\n" +
                "| ${board[0][4]} |  ${board[1][4]} |  ${board[2][4]} |  ${board[3][4]} |  ${board[4][4]} |"
    }

}