package com.intelligentblokus.intelligentblokus.piece.impl

import com.intelligentblokus.intelligentblokus.BlokusPieceEnum
import com.intelligentblokus.intelligentblokus.BlokusPieceVariation
import com.intelligentblokus.intelligentblokus.piece.BlokusPiece
import org.springframework.stereotype.Component

@Component
object BlokusPieceO : BlokusPiece {
    private val variations = listOf(
            createVariation(listOf(
                    listOf(1, 1),
                    listOf(1, 1)))
    )

    override fun getEnum(): BlokusPieceEnum = BlokusPieceEnum.O

    override fun getVariations(): List<BlokusPieceVariation> {
        return variations
    }
}