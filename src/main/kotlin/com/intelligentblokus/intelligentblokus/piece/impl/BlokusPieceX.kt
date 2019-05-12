package com.intelligentblokus.intelligentblokus.piece.impl

import com.intelligentblokus.intelligentblokus.piece.AbstractBlokusPiece
import com.intelligentblokus.intelligentblokus.piece.BlokusPieceEnum
import com.intelligentblokus.intelligentblokus.piece.BlokusPieceVariation
import org.springframework.stereotype.Component

@Component
class BlokusPieceX : AbstractBlokusPiece() {
    private val variations = listOf(
            this.createVariation(listOf(
                    listOf(0, 1, 0),
                    listOf(1, 1, 1),
                    listOf(0, 1, 0)))
    )

    override fun getEnum(): BlokusPieceEnum = BlokusPieceEnum.X

    override fun getVariations(): List<BlokusPieceVariation> {
        return variations
    }
}