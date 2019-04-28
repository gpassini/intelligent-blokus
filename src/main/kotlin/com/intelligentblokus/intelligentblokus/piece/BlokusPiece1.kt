package com.intelligentblokus.intelligentblokus.piece

import com.intelligentblokus.intelligentblokus.BlokusPiece
import com.intelligentblokus.intelligentblokus.BlokusPieceEnum
import com.intelligentblokus.intelligentblokus.BlokusPieceVariation
import org.springframework.stereotype.Component

@Component
object BlokusPiece1 : BlokusPiece {
    private val variations = listOf(createVariation(listOf(listOf(1))))

    override fun getEnum() = BlokusPieceEnum.ONE

    override fun getVariations(): List<BlokusPieceVariation> {
        return variations
    }
}