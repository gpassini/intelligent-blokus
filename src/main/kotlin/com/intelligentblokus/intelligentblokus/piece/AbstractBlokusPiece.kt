package com.intelligentblokus.intelligentblokus.piece

abstract class AbstractBlokusPiece : BlokusPiece {

    override fun toString(): String {
        return getEnum().name
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is BlokusPiece) return false
        if (getEnum() != other.getEnum()) return false
        return true
    }

    override fun hashCode(): Int {
        return getEnum().hashCode()
    }

}