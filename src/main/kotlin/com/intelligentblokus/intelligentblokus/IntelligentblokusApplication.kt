package com.intelligentblokus.intelligentblokus

import com.intelligentblokus.intelligentblokus.property.BlokusProperties
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.runApplication

@SpringBootApplication
@EnableConfigurationProperties(BlokusProperties::class)
class IntelligentblokusApplication

fun main(args: Array<String>) {
    runApplication<IntelligentblokusApplication>(*args)
}
