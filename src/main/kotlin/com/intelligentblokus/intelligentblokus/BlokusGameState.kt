package com.intelligentblokus.intelligentblokus

data class BlokusGameState(
        val board: BlokusBoard,
        /**
         * Current ordered players
         */
        val players: Pair<BlokusPlayerState, BlokusPlayerState>)
