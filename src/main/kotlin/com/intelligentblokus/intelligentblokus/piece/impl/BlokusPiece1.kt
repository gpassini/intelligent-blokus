package com.intelligentblokus.intelligentblokus.piece.impl

import com.intelligentblokus.intelligentblokus.piece.BlokusPiece
import com.intelligentblokus.intelligentblokus.piece.BlokusPieceEnum
import com.intelligentblokus.intelligentblokus.piece.BlokusPieceVariation
import org.springframework.stereotype.Component

@Component
object BlokusPiece1 : BlokusPiece {
    private val variations = listOf(createVariation(listOf(listOf(1))))

    override fun getEnum() = BlokusPieceEnum.ONE

    override fun getVariations(): List<BlokusPieceVariation> {
        return variations
    }
}