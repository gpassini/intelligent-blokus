package com.intelligentblokus.intelligentblokus

import com.intelligentblokus.intelligentblokus.piece.BlokusPiece
import com.intelligentblokus.intelligentblokus.play_strategy.BlokusPlayerEnum

data class BlokusGameState(
        val board: BlokusBoard,
        /**
         * Current ordered players list
         */
        val players: LinkedHashMap<BlokusPlayerEnum, List<BlokusPiece>>)
