package com.intelligentblokus.intelligentblokus.piece

import com.intelligentblokus.intelligentblokus.BlokusPiece
import com.intelligentblokus.intelligentblokus.BlokusPieceEnum
import com.intelligentblokus.intelligentblokus.BlokusPieceVariation
import org.springframework.stereotype.Component

@Component
object BlokusPiece2: BlokusPiece {
    private val variations = listOf(
            createVariation(listOf(listOf(1, 1))),
            createVariation(listOf(listOf(1), listOf(1)))
    )

    override fun getEnum(): BlokusPieceEnum = BlokusPieceEnum.TWO

    override fun getVariations(): List<BlokusPieceVariation> {
        return variations
    }
}