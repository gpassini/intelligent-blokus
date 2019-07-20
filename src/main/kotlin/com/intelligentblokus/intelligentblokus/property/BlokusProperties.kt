package com.intelligentblokus.intelligentblokus.property

import com.intelligentblokus.intelligentblokus.play_strategy.BlokusPlayStrategyEnum
import com.intelligentblokus.intelligentblokus.play_strategy.BlokusPlayerEnum
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.validation.annotation.Validated
import javax.validation.constraints.Size

@ConfigurationProperties(prefix = "blokus")
@Validated
class BlokusProperties {
    /**
     * Ordered players and their strategies.
     */
    @Size(min = 2, max = 2, message = "You must define 2 players.")
    lateinit var players: LinkedHashMap<BlokusPlayerEnum, BlokusPlayStrategyEnum>
}
