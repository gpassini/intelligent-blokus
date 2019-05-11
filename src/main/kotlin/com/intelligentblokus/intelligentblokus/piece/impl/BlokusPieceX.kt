package com.intelligentblokus.intelligentblokus.piece.impl

import com.intelligentblokus.intelligentblokus.piece.BlokusPiece
import com.intelligentblokus.intelligentblokus.piece.BlokusPieceEnum
import com.intelligentblokus.intelligentblokus.piece.BlokusPieceVariation
import org.springframework.stereotype.Component

@Component
object BlokusPieceX : BlokusPiece {
    private val variations = listOf(
            createVariation(listOf(
                    listOf(0, 1, 0),
                    listOf(1, 1, 1),
                    listOf(0, 1, 0)))
    )

    override fun getEnum(): BlokusPieceEnum = BlokusPieceEnum.X

    override fun getVariations(): List<BlokusPieceVariation> {
        return variations
    }
}