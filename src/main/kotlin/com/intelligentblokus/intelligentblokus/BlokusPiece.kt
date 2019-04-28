package com.intelligentblokus.intelligentblokus

interface BlokusPiece {

    fun getEnum(): BlokusPieceEnum

    fun getVariations(): List<BlokusPieceVariation>

    fun createVariation(variation: List<List<Int>>): BlokusPieceVariation {
        return BlokusPieceVariation(this, variation)
    }
}
