package com.intelligentblokus.intelligentblokus.play_strategy

import com.intelligentblokus.intelligentblokus.BlokusGameState
import com.intelligentblokus.intelligentblokus.BlokusMove

interface BlokusPlayStrategy {

    fun play(gameState: BlokusGameState): BlokusMove

    fun getEnum(): BlokusPlayStrategyEnum
}