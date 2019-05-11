package com.intelligentblokus.intelligentblokus.exception

import com.intelligentblokus.intelligentblokus.play_strategy.BlokusPlayerEnum

class NoMoveLeftException(val playerEnum: BlokusPlayerEnum) : Exception()