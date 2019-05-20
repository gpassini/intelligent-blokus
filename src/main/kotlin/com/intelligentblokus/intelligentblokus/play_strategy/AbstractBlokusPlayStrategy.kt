package com.intelligentblokus.intelligentblokus.play_strategy

abstract class AbstractBlokusPlayStrategy : BlokusPlayStrategy {

    override fun toString(): String = getEnum().toString()
}