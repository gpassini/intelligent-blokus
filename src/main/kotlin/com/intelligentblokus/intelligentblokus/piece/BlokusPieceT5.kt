package com.intelligentblokus.intelligentblokus.piece

import com.intelligentblokus.intelligentblokus.BlokusPiece
import com.intelligentblokus.intelligentblokus.BlokusPieceEnum
import com.intelligentblokus.intelligentblokus.BlokusPieceVariation
import org.springframework.stereotype.Component

@Component
object BlokusPieceT5 : BlokusPiece {
    private val variations = listOf(
            createVariation(listOf(
                    listOf(1, 1, 1),
                    listOf(0, 1, 0),
                    listOf(0, 1, 0))),
            createVariation(listOf(
                    listOf(1, 0, 0),
                    listOf(1, 1, 1),
                    listOf(1, 0, 0))),
            createVariation(listOf(
                    listOf(0, 1, 0),
                    listOf(0, 1, 0),
                    listOf(1, 1, 1))),
            createVariation(listOf(
                    listOf(0, 0, 1),
                    listOf(1, 1, 1),
                    listOf(0, 0, 1)))
    )

    override fun getEnum(): BlokusPieceEnum = BlokusPieceEnum.T5

    override fun getVariations(): List<BlokusPieceVariation> {
        return variations
    }
}