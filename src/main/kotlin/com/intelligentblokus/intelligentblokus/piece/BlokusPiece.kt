package com.intelligentblokus.intelligentblokus.piece

import com.intelligentblokus.intelligentblokus.BlokusPieceEnum
import com.intelligentblokus.intelligentblokus.BlokusPieceVariation

interface BlokusPiece {

    fun getEnum(): BlokusPieceEnum

    fun getVariations(): List<BlokusPieceVariation>

    fun createVariation(variation: List<List<Int>>): BlokusPieceVariation {
        return BlokusPieceVariation(this, variation)
    }
}
