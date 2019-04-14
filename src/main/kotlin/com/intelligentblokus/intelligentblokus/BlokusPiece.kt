package com.intelligentblokus.intelligentblokus

interface BlokusPiece {

    fun getEnum(): BlokusPieceEnum

    fun getVariations(): List<BlokusPieceVariation>
}
