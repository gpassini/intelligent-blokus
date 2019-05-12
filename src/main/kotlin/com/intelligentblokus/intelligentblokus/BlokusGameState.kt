package com.intelligentblokus.intelligentblokus

import java.util.Optional
import kotlin.NoSuchElementException

data class BlokusGameState(val board: BlokusBoard, val players: List<BlokusPlayer>) {
    fun getNextPlayer(): BlokusPlayer = Optional.ofNullable(players.firstOrNull()).orElseThrow { NoSuchElementException("No player found.") }
}
