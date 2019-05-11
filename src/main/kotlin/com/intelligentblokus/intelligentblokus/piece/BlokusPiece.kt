package com.intelligentblokus.intelligentblokus.piece

interface BlokusPiece {

    fun getEnum(): BlokusPieceEnum

    fun getVariations(): List<BlokusPieceVariation>

    fun createVariation(variation: List<List<Int>>): BlokusPieceVariation {
        return BlokusPieceVariation(this, variation)
    }
}
