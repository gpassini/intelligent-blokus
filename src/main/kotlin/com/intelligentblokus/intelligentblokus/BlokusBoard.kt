package com.intelligentblokus.intelligentblokus

import com.intelligentblokus.intelligentblokus.play_strategy.BlokusPlayerEnum
import org.slf4j.LoggerFactory

/**
 * Blokus board model and utility functions.
 */
class BlokusBoard(
        /**
         * 2D matrix representing the board.
         */
        private val board: List<List<Int>> = List(BOARD_SIZE) { List(BOARD_SIZE) { 0 } }
) {
    companion object {
        const val BOARD_SIZE = 14
        private const val START_OFFSET = 5

        @JvmStatic
        private fun isOverStartingPoint(move: BlokusMove): Boolean {
            val (playerEnum, pieceVariation, x, y) = move
            val startingPosition = getStartingPosition(playerEnum)
            val xDistance = startingPosition - x
            val yDistance = startingPosition - y
            val pieceShape = pieceVariation.shape
            return xDistance in 0 until pieceShape.size &&
                    yDistance in 0 until pieceShape[0].size &&
                    pieceShape[xDistance][yDistance] == 1
        }

        @JvmStatic
        private fun getStartingPosition(playerEnum: BlokusPlayerEnum): Int {
            return when (playerEnum) {
                BlokusPlayerEnum.BLACK -> START_OFFSET - 1
                BlokusPlayerEnum.WHITE -> BOARD_SIZE - START_OFFSET
            }
        }
    }

    private val log = LoggerFactory.getLogger(javaClass)

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
            throw IllegalArgumentException("The move is invalid.")
        }

        val cloneBoard = board.map { it.toMutableList() }.toList()
        val (player, pieceVariation, x, y) = blokusMove
        val piece = pieceVariation.shape
        val pieceXSize = piece.size
        val pieceYSize = piece[0].size

        for (i in 0 until pieceXSize) {
            for (j in 0 until pieceYSize) {
                if (piece[i][j] != 0) {
                    cloneBoard[i + x][j + y] = player.code
                }
            }
        }

        return BlokusBoard(cloneBoard)
    }

    fun isEmpty(x: Int, y: Int): Boolean {
        return this.peek(x, y) == 0
    }

    fun isNotEmpty(x: Int, y: Int): Boolean {
        return this.isEmpty(x, y).not()
    }

    /**
     * Returns true if the move is valid.
     */
    fun isValidMove(move: BlokusMove): Boolean {
        val (player, _, x, y) = move
        controlOutOfBoundsPosition(x, y)
        if (isFirstMove(player)) {
            log.debug("Player $player first move.")
            return isOverStartingPoint(move)
        }
        return isEmpty(move.pieceVariation.shape, x, y)
                && touchesBySide(move).not()
                && isPieceLinkedDiagonally(move)
    }

    fun copy(): BlokusBoard = BlokusBoard(board.map { it.toList() }.toList())

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

    private fun isEmpty(piece: List<List<Int>>, x: Int, y: Int): Boolean {
        val pieceXSize = piece.size
        val pieceYSize = piece[0].size
        for (i in 0 until pieceXSize) {
            for (j in 0 until pieceYSize) {
                if (piece[i][j] != 0 && isNotEmpty(i + x, j + y)) {
                    log.debug("The position [ ${i + x} , ${j + y} ] is taken.")
                    return false
                }
            }
        }
        return true
    }

    /**
     * Returns true if it is the first move from the given play_strategy.
     */
    private fun isFirstMove(playerEnum: BlokusPlayerEnum): Boolean {
        return board.flatten().none { it == playerEnum.code }
    }

    /**
     * Returns true if the attempted move results is a piece touching another piece from the same play_strategy by the side (it is an invalid move).
     */
    private fun touchesBySide(move: BlokusMove): Boolean {
        val (player, pieceVariation, x, y) = move
        val piece = pieceVariation.shape
        val pieceXSize = piece.size
        val pieceYSize = piece[0].size
        for (i in 0 until pieceXSize) {
            for (j in 0 until pieceYSize) {
                if (piece[i][j] != 0 && hasSamePlayerNeighbor(player, i + x, j + y)) {
                    log.debug("Invalid move. The piece touches a same color tile at position [ ${i + x} , ${j + y} ]")
                    return true
                }
            }
        }
        return false
    }

    /**
     * Throws an [IllegalArgumentException] if at least one of the given coordinates does not exist on the board.
     */
    private fun controlOutOfBoundsPosition(x: Int, y: Int) {
        if (x < 0 || x >= BOARD_SIZE || y < 0 || y >= BOARD_SIZE) {
            throw IllegalArgumentException("One of the coordinates [ $x, $y ] is invalid. Expected 0 <= [coordinate] < $BOARD_SIZE.")
        }
    }

    /**
     * Returns true if one of the four adjacent tiles (upper, bottom, right, left) to the given position is already occupied by a piece of the given play_strategy.
     */
    private fun hasSamePlayerNeighbor(playerEnum: BlokusPlayerEnum, x: Int, y: Int): Boolean {
        val playerCode = playerEnum.code
        return x - 1 >= 0 && peek(x - 1, y) == playerCode
                || x + 1 < BOARD_SIZE && peek(x + 1, y) == playerCode
                || y - 1 >= 0 && peek(x, y - 1) == playerCode
                || y + 1 < BOARD_SIZE && peek(x, y + 1) == playerCode
    }

    /**
     * Returns true if the attempted move results in a piece connected by at least one corner to another piece of the same play_strategy (it is a requirement for a valid move).
     */
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
        log.debug("Invalid move. The piece is not linked to another piece of the same play_strategy")
        return false
    }

    /**
     * Returns true if one of the four cornering tiles of the given position is already occupied by a piece of the given play_strategy.
     */
    private fun hasSamePlayerDiagonalNeighbor(playerEnum: BlokusPlayerEnum, x: Int, y: Int): Boolean {
        val playerCode = playerEnum.code
        return x - 1 >= 0 && y - 1 >= 0 && peek(x - 1, y - 1) == playerCode
                || x + 1 < BOARD_SIZE && y - 1 >= 0 && peek(x + 1, y - 1) == playerCode
                || x - 1 >= 0 && y + 1 < BOARD_SIZE && peek(x - 1, y + 1) == playerCode
                || x + 1 < BOARD_SIZE && y + 1 < BOARD_SIZE && peek(x + 1, y + 1) == playerCode
    }

}