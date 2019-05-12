package com.intelligentblokus.intelligentblokus.piece.impl

import com.intelligentblokus.intelligentblokus.piece.AbstractBlokusPiece
import com.intelligentblokus.intelligentblokus.piece.BlokusPieceEnum
import com.intelligentblokus.intelligentblokus.piece.BlokusPieceVariation
import org.springframework.stereotype.Component

@Component
class BlokusPieceL4 : AbstractBlokusPiece() {
    private val variations = listOf(
            this.createVariation(listOf(
                    listOf(1, 0),
                    listOf(1, 0),
                    listOf(1, 1))),
            this.createVariation(listOf(
                    listOf(0, 0, 1),
                    listOf(1, 1, 1))),
            this.createVariation(listOf(
                    listOf(1, 1),
                    listOf(0, 1),
                    listOf(0, 1))),
            this.createVariation(listOf(
                    listOf(1, 1, 1),
                    listOf(1, 0, 0))),
            this.createVariation(listOf(
                    listOf(0, 1),
                    listOf(0, 1),
                    listOf(1, 1))),
            this.createVariation(listOf(
                    listOf(1, 1, 1),
                    listOf(0, 0, 1))),
            this.createVariation(listOf(
                    listOf(1, 1),
                    listOf(1, 0),
                    listOf(1, 0))),
            this.createVariation(listOf(
                    listOf(1, 0, 0),
                    listOf(1, 1, 1)))
    )

    override fun getEnum(): BlokusPieceEnum = BlokusPieceEnum.L4

    override fun getVariations(): List<BlokusPieceVariation> {
        return variations
    }
}