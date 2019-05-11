package com.intelligentblokus.intelligentblokus.piece

import java.util.stream.Stream

enum class BlokusPieceEnum(name: String) {
    ONE("1"),
    TWO("2"),
    I3("I3"),
    V3("V3"),
    T4("T4"),
    O("O"),
    I4("I4"),
    L4("L4"),
    Z4("Z4"),
    F("F"),
    X("X"),
    P("P"),
    W("W"),
    Z5("Z5"),
    Y("Y"),
    L5("L5"),
    U("U"),
    T5("T5"),
    V5("V5"),
    N("N"),
    I5("I5");

    companion object {
        fun get(name: String): BlokusPieceEnum {
            return Stream
                    .of(*values())
                    .filter { it.name.equals(name, true) }
                    .findAny()
                    .orElseThrow { IllegalArgumentException("[ $name ] is not a valid Blokus piece name.") }
        }
    }
}
