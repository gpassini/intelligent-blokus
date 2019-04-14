package com.intelligentblokus.intelligentblokus.piece

import com.intelligentblokus.intelligentblokus.BlokusPiece
import com.intelligentblokus.intelligentblokus.BlokusPieceEnum
import com.intelligentblokus.intelligentblokus.BlokusPieceVariation
import java.util.*

class BlokusPiece1: BlokusPiece {
    override fun getEnum(): BlokusPieceEnum = BlokusPieceEnum.ONE

    override fun getVariations(): List<BlokusPieceVariation> {
        return Collections.singletonList(BlokusPieceVariation(this, listOf(listOf(1))))
    }
}