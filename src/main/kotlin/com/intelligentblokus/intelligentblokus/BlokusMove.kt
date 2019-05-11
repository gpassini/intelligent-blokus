package com.intelligentblokus.intelligentblokus

import com.intelligentblokus.intelligentblokus.piece.BlokusPieceVariation
import com.intelligentblokus.intelligentblokus.play_strategy.BlokusPlayerEnum

data class BlokusMove(val playerEnum: BlokusPlayerEnum, val pieceVariation: BlokusPieceVariation, val x: Int, val y: Int)
