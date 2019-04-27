package com.intelligentblokus.intelligentblokus.piece

import com.intelligentblokus.intelligentblokus.BlokusPiece
import com.intelligentblokus.intelligentblokus.BlokusPieceEnum
import com.intelligentblokus.intelligentblokus.BlokusPieceVariation
import java.util.*

class BlokusPiece2: BlokusPiece {
    override fun getEnum(): BlokusPieceEnum = BlokusPieceEnum.TWO

    override fun getVariations(): List<BlokusPieceVariation> {
        return Arrays.asList(
                BlokusPieceVariation(this, listOf(listOf(1, 1))),
                BlokusPieceVariation(this, listOf(listOf(1), listOf(1)))
        )
    }
}