package com.intelligentblokus.intelligentblokus

import com.intelligentblokus.intelligentblokus.piece.BlokusPiece
import com.intelligentblokus.intelligentblokus.play_strategy.BlokusPlayerEnum

data class BlokusPlayerState(val playerEnum: BlokusPlayerEnum, val pieces: Set<BlokusPiece>)