package com.intelligentblokus.intelligentblokus.piece.impl

import com.intelligentblokus.intelligentblokus.piece.BlokusPiece
import com.intelligentblokus.intelligentblokus.piece.BlokusPieceEnum
import com.intelligentblokus.intelligentblokus.piece.BlokusPieceVariation
import org.springframework.stereotype.Component

@Component
object BlokusPieceF : BlokusPiece {
    private val variations = listOf(
            createVariation(listOf(
                    listOf(0, 1, 1),
                    listOf(1, 1, 0),
                    listOf(0, 1, 0))),
            createVariation(listOf(
                    listOf(0, 1, 0),
                    listOf(1, 1, 1),
                    listOf(0, 0, 1))),
            createVariation(listOf(
                    listOf(0, 1, 0),
                    listOf(0, 1, 1),
                    listOf(1, 1, 0))),
            createVariation(listOf(
                    listOf(1, 0, 0),
                    listOf(1, 1, 1),
                    listOf(0, 1, 0))),
            createVariation(listOf(
                    listOf(1, 1, 0),
                    listOf(0, 1, 1),
                    listOf(0, 1, 0))),
            createVariation(listOf(
                    listOf(0, 0, 1),
                    listOf(1, 1, 1),
                    listOf(0, 1, 0))),
            createVariation(listOf(
                    listOf(0, 1, 0),
                    listOf(1, 1, 0),
                    listOf(0, 1, 1))),
            createVariation(listOf(
                    listOf(0, 1, 0),
                    listOf(1, 1, 1),
                    listOf(1, 0, 0)))
    )

    override fun getEnum(): BlokusPieceEnum = BlokusPieceEnum.F

    override fun getVariations(): List<BlokusPieceVariation> {
        return variations
    }
}