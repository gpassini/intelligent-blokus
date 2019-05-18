package com.intelligentblokus.intelligentblokus.property

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.stereotype.Component

/**
 * Players of the game.
 */
@Component
@ConfigurationProperties(prefix = "blokus.players")
data class BlokusProperties(
        /**
         * A random player
         */
        var random: Boolean = true,

        /**
         * A big pieces player
         */
        var bigPieces: Boolean = false
)
