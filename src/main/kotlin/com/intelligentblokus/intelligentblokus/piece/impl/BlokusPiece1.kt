package com.intelligentblokus.intelligentblokus.piece.impl

import com.intelligentblokus.intelligentblokus.piece.AbstractBlokusPiece
import com.intelligentblokus.intelligentblokus.piece.BlokusPieceEnum
import com.intelligentblokus.intelligentblokus.piece.BlokusPieceVariation
import org.springframework.stereotype.Component

@Component
class BlokusPiece1 : AbstractBlokusPiece() {
    private val variations = listOf(this.createVariation(listOf(listOf(1))))

    override fun getEnum() = BlokusPieceEnum.ONE

    override fun getVariations(): List<BlokusPieceVariation> {
        return variations
    }
}