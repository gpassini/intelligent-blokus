package com.intelligentblokus.intelligentblokus.piece

import com.intelligentblokus.intelligentblokus.piece.impl.BlokusPiece1
import com.intelligentblokus.intelligentblokus.piece.impl.BlokusPiece2
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

internal class AbstractBlokusPieceTest {

    @Test
    internal fun testEqualsNull() {
        assertThat(BlokusPiece1()).isNotEqualTo(null)
    }

    @Test
    internal fun testEqualsAny() {
        assertThat(BlokusPiece1()).isNotEqualTo(Any())
    }

    @Test
    internal fun testEqualsDifferent() {
        assertThat(BlokusPiece1()).isNotEqualTo(BlokusPiece2())
    }

    @Test
    internal fun testEqualsSameInstance() {
        val blokusPiece1 = BlokusPiece1()
        assertThat(blokusPiece1).isEqualTo(blokusPiece1)
    }

    @Test
    internal fun testEqualsSameType() {
        assertThat(BlokusPiece1()).isEqualTo(BlokusPiece1())
    }
}