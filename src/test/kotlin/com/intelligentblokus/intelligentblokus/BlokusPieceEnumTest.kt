package com.intelligentblokus.intelligentblokus

import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.collection.IsCollectionWithSize.hasSize
import org.junit.jupiter.api.Test

internal class BlokusPieceEnumTest {

    @Test
    internal fun piecesNamesShouldBeUnique() {
        val pieces = BlokusPieceEnum.values()
        val piecesNumber = pieces.size
        val namesSet = pieces.map { it.name }.toSet()
        assertThat(namesSet, hasSize(piecesNumber))
    }

}