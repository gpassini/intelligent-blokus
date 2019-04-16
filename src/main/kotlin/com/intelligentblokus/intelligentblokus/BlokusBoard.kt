package com.intelligentblokus.intelligentblokus

import org.slf4j.LoggerFactory

/**
 * Blokus board model and utility functions.
 */
class BlokusBoard(
        /**
         * 2D matrix representing the board.
         */
        private val board: MutableList<MutableList<Int>> = initBoard()
) {
    private val log = LoggerFactory.getLogger(javaClass)

    companion object {
        const val BOARD_SIZE = 5

        @JvmStatic
        private fun initBoard(): MutableList<MutableList<Int>> = IntRange(0, BOARD_SIZE - 1).map { initLine() }.toCollection(mutableListOf())

        @JvmStatic
        private fun initLine(): MutableList<Int> = IntRange(0, BOARD_SIZE - 1).map { 0 }.toCollection(mutableListOf())
    }

    /**
     * Returns the value of the tile at the given position.
     */
    fun peek(x: Int, y: Int): Int {
        controlOutOfBoundsPosition(x, y)
        return board[x][y]
    }

    /**
     * Plays the given move, if it is valid.
     */
    fun playMove(blokusMove: BlokusMove): BlokusBoard {
        if (isValidMove(blokusMove).not()) {
            throw IllegalArgumentException("The move is invalid")
        }
        val (player, pieceVariation, x, y) = blokusMove
        controlOutOfBoundsPosition(x, y)
        val piece = pieceVariation.shape
        val pieceXSize = piece.size
        val pieceYSize = piece[0].size
        for (i in 0 until pieceXSize) {
            for (j in 0 until pieceYSize) {
                if (piece[i][j] != 0) {
                    board[i + x][j + y] = player.code
                }
            }
        }
        return this
    }

    fun isEmpty(x: Int, y: Int): Boolean {
        return this.peek(x, y) == 0
    }

    fun isNotEmpty(x: Int, y: Int): Boolean {
        return this.isEmpty(x, y).not()
    }

    fun isEmpty(piece: List<List<Int>>, x: Int, y: Int): Boolean {
        val pieceXSize = piece.size
        val pieceYSize = piece[0].size
        for (i in 0 until pieceXSize) {
            for (j in 0 until pieceYSize) {
                if (piece[i][j] != 0 && isNotEmpty(i + x, j + y)) {
                    log.debug("The position [ ${i+x} , ${j+y} ] is taken.")
                    return false
                }
            }
        }
        return true
    }

    fun isNotEmpty(piece: List<List<Int>>, x: Int, y: Int): Boolean {
        return isEmpty(piece, x, y).not()
    }

    fun isValidMove(move: BlokusMove): Boolean {
        val (player, _, x, y) = move
        controlOutOfBoundsPosition(x, y)
        if (isFirstMove(player)) {
            log.debug("Player $player first move.")
            // For now, let the player chose his first move without constraints.
            return true
        }
        return isEmpty(move.pieceVariation.shape, x, y)
                && touchesBySide(move).not()
                && isPieceLinkedDiagonally(move)
    }

    private fun isFirstMove(player: BlokusPlayer): Boolean {
        return board.flatten().none { it == player.code }
    }

    private fun touchesBySide(move: BlokusMove): Boolean {
        val (player, pieceVariation, x, y) = move
        val piece = pieceVariation.shape
        val pieceXSize = piece.size
        val pieceYSize = piece[0].size
        for (i in 0 until pieceXSize) {
            for (j in 0 until pieceYSize) {
                if (piece[i][j] != 0 && hasSamePlayerNeighbor(player, i + x, j + y)) {
                    log.debug("Invalid move. The piece touches a same color tile at position [ ${i+x} , ${j+y} ]")
                    return true
                }
            }
        }
        return false
    }

    private fun hasSamePlayerNeighbor(player: BlokusPlayer, x: Int, y: Int): Boolean {
        val playerCode = player.code
        return x - 1 >= 0 && peek(x - 1, y) == playerCode
                || x + 1 < BOARD_SIZE && peek(x + 1, y) == playerCode
                || y - 1 >= 0 && peek(x, y - 1) == playerCode
                || y + 1 < BOARD_SIZE && peek(x, y + 1) == playerCode
    }

    private fun isPieceLinkedDiagonally(move: BlokusMove): Boolean {
        val (player, pieceVariation, x, y) = move
        val piece = pieceVariation.shape
        val pieceXSize = piece.size
        val pieceYSize = piece[0].size
        for (i in 0 until pieceXSize) {
            for (j in 0 until pieceYSize) {
                if (piece[i][j] != 0 && hasSamePlayerDiagonalNeighbor(player, i + x, j + y)) {
                    return true
                }
            }
        }
        log.debug("Invalid move. The piece is not linked to another piece of the same player")
        return false
    }

    private fun hasSamePlayerDiagonalNeighbor(player: BlokusPlayer, x: Int, y: Int): Boolean {
        val playerCode = player.code
        return x - 1 >= 0 && y - 1 >= 0 && peek(x - 1, y - 1) == playerCode
                || x + 1 < BOARD_SIZE && y - 1 >= 0 && peek(x + 1, y - 1) == playerCode
                || x - 1 >= 0 && y + 1 < BOARD_SIZE && peek(x - 1, y + 1) == playerCode
                || x + 1 < BOARD_SIZE && y + 1 < BOARD_SIZE && peek(x + 1, y + 1) == playerCode
    }

    /**
     * Returns a textual representation of this board.
     */
    override fun toString(): String {
        val stringBuilder = StringBuilder()
        for (line in board) {
            for (tile in line) {
                stringBuilder.append("| ").append(tile).append(" ")
            }
            stringBuilder.append("|\n")
        }
        return stringBuilder.toString()
    }

    /**
     * Throws an [IllegalArgumentException] if at least one of the given coordinates does not exist on the board.
     */
    private fun controlOutOfBoundsPosition(x: Int, y: Int) {
        if (x < 0 || x >= BOARD_SIZE || y < 0 || y >= BOARD_SIZE) {
            throw IllegalArgumentException("One of the coordinates [ $x, $y ] is invalid. Expected 0 <= [coordinate] < $BOARD_SIZE.")
        }
    }

}