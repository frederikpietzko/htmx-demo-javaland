package de.fpietzko.htmxdemo.config.websocket

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties(prefix = "conference")
data class ConferenceConfig(
    val conferenceName: String,
    val conferenceYear: Int,
)