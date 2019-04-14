package com.intelligentblokus.intelligentblokus

import java.util.stream.Stream

enum class BlokusPieceEnum(name: String) {
    ONE("1"),
    TWO("2");

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
