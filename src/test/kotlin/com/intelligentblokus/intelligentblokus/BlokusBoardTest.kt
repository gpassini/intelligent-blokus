package com.intelligentblokus.intelligentblokus

import com.intelligentblokus.intelligentblokus.piece.BlokusPiece1
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.core.Is.`is`
import org.hamcrest.core.IsNot.not
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.springframework.util.StringUtils

internal class BlokusBoardTest {

    private companion object {
        const val EMPTY_TILE = 0
        val MOVE = BlokusMove(BlokusPlayer.BLACK, BlokusPiece1().getVariations()[0], 0, 0)
    }

    @Test
    internal fun testInitialization() {
        val blokusBoard = BlokusBoard()
        for (i in 0 until BlokusBoard.BOARD_SIZE) {
            for (j in 0 until BlokusBoard.BOARD_SIZE) {
                assertThat("Tile at position [ $i, $j ] was not correctly initialized.",
                        blokusBoard.peek(i, j),
                        `is`(EMPTY_TILE))
            }
        }
    }

    @Test
    internal fun testIsTileEmptyTrue() {
        val blokusBoard = BlokusBoard()
        assertThat(blokusBoard.isEmpty(0, 0), `is`(true))
        assertThat(blokusBoard.isNotEmpty(0, 0), `is`(false))
    }

    @Test
    internal fun testIsTileEmptyFalse() {
        val blokusBoard = BlokusBoard().playMove(MOVE)
        assertThat(blokusBoard.isEmpty(0, 0), `is`(false))
        assertThat(blokusBoard.isNotEmpty(0, 0), `is`(true))
    }

    @Test
    internal fun testPlayMoveAndPeek() {
        val blokusBoard = BlokusBoard().playMove(MOVE)
        assertThat(blokusBoard.peek(0, 0), `is`(not(EMPTY_TILE)))
    }

    @Test
    internal fun testPeekPositionOutOfBounds() {
        val coordinateTooLow = -1
        val coordinateOk = 0
        val coordinateTooHigh = BlokusBoard.BOARD_SIZE

        val xCoordinateTooLowException = assertThrows<IllegalArgumentException> { BlokusBoard().peek(coordinateTooLow, coordinateOk) }
        assertThat(xCoordinateTooLowException.message, `is`("One of the coordinates [ $coordinateTooLow, $coordinateOk ] is invalid. Expected 0 <= [coordinate] < ${BlokusBoard.BOARD_SIZE}."))

        val xCoordinateTooHighException = assertThrows<IllegalArgumentException> { BlokusBoard().peek(coordinateTooHigh, coordinateOk) }
        assertThat(xCoordinateTooHighException.message, `is`("One of the coordinates [ $coordinateTooHigh, $coordinateOk ] is invalid. Expected 0 <= [coordinate] < ${BlokusBoard.BOARD_SIZE}."))

        val yCoordinateTooLowException = assertThrows<IllegalArgumentException> { BlokusBoard().peek(coordinateOk, coordinateTooLow) }
        assertThat(yCoordinateTooLowException.message, `is`("One of the coordinates [ $coordinateOk, $coordinateTooLow ] is invalid. Expected 0 <= [coordinate] < ${BlokusBoard.BOARD_SIZE}."))

        val yCoordinateTooHighException = assertThrows<IllegalArgumentException> { BlokusBoard().peek(coordinateOk, coordinateTooHigh) }
        assertThat(yCoordinateTooHighException.message, `is`("One of the coordinates [ $coordinateOk, $coordinateTooHigh ] is invalid. Expected 0 <= [coordinate] < ${BlokusBoard.BOARD_SIZE}."))
    }

    @Test
    internal fun testPlacePieceAtOccupiedPositionForbidden() {
        val blokusBoard = BlokusBoard()
        blokusBoard.playMove(MOVE)
        val illegalArgumentException = assertThrows<IllegalArgumentException> { blokusBoard.playMove(MOVE) }
        assertThat(illegalArgumentException.message, `is`("The piece cannot be placed at the position [${MOVE.x}, ${MOVE.y}] because the space is already taken."))
    }

    @Test
    internal fun testToString() {
        assertThat(StringUtils.countOccurrencesOf(BlokusBoard().toString(), EMPTY_TILE.toString()), `is`(BlokusBoard.BOARD_SIZE * BlokusBoard.BOARD_SIZE))
    }
}