package com.intelligentblokus.intelligentblokus.piece

import com.intelligentblokus.intelligentblokus.BlokusPieceEnum
import com.intelligentblokus.intelligentblokus.BlokusPieceEnum.*
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.core.IsEqual.equalTo
import org.hamcrest.core.IsNull.notNullValue
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.shell.jline.InteractiveShellApplicationRunner
import org.springframework.shell.jline.ScriptShellApplicationRunner
import org.springframework.test.context.junit.jupiter.SpringExtension

@ExtendWith(SpringExtension::class)
@SpringBootTest(properties = [
    InteractiveShellApplicationRunner.SPRING_SHELL_INTERACTIVE_ENABLED + "=false",
    ScriptShellApplicationRunner.SPRING_SHELL_SCRIPT_ENABLED + "=false"])
internal class BlokusPieceImplTest {

    private companion object {
        private val piecesByTilesNumber: Map<Int, List<BlokusPieceEnum>> = mapOf(
                Pair(1, listOf(ONE)),
                Pair(2, listOf(TWO)),
                Pair(3, listOf(V3, I3)),
                Pair(4, listOf(T4, O, L4, I4, Z4)),
                Pair(5, listOf(F, X, P, W, Z5, Y, L5, U, T5, V5, N, I5))
        )
    }

    @Suppress("SpringJavaInjectionPointsAutowiringInspection")
    @Autowired
    private lateinit var pieces: List<BlokusPiece>

    @Test
    internal fun shouldHaveOneBlokusPieceImplPerEnum() {
        values().forEach { assertPieceImplExists(it) }
    }

    private fun assertPieceImplExists(pieceEnum: BlokusPieceEnum) {
        assertThat("No implementation found for piece [ $pieceEnum ].", pieces.find { pieceEnum == it.getEnum() }, notNullValue())
    }

    @Test
    internal fun piecesShouldHaveTheCorrectNumberOfTiles() {
        piecesByTilesNumber.forEach { (tilesNumber, piecesEnums) -> assertHasNumberOfTiles(piecesEnums, tilesNumber) }
    }

    private fun assertHasNumberOfTiles(piecesEnums: List<BlokusPieceEnum>, expectedTilesNumber: Int) {
        pieces.filter { piecesEnums.contains(it.getEnum()) }.forEach { assertHasNumberOfTiles(it, expectedTilesNumber) }
    }

    private fun assertHasNumberOfTiles(piece: BlokusPiece, expectedTilesNumber: Int) {
        piece.getVariations().map { it.shape }.forEach { assertHasNumberOfTiles(it, expectedTilesNumber, piece.getEnum()) }
    }

    private fun assertHasNumberOfTiles(shape: List<List<Int>>, expectedTilesNumber: Int, pieceEnum: BlokusPieceEnum) {
        val actualTilesNumber = shape.flatten().reduce { acc, i -> acc + i }
        assertThat("Shape $shape from piece $pieceEnum does not have strictly $expectedTilesNumber tile(s).", actualTilesNumber, equalTo(expectedTilesNumber))
    }
}