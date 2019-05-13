package com.intelligentblokus.intelligentblokus.piece

data class BlokusPieceVariation(val blokusPiece: BlokusPiece, val shape: List<List<Int>>) {
    fun getTilesNumber(): Int = shape.flatten().reduce { acc, i -> acc + i }
}
