package de.fpietzko.htmxdemo

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.ConfigurationPropertiesScan
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean
import org.springframework.scheduling.annotation.EnableScheduling
import org.springframework.session.MapSessionRepository
import org.springframework.session.config.annotation.web.http.EnableSpringHttpSession
import java.util.concurrent.ConcurrentHashMap

@SpringBootApplication
@EnableSpringHttpSession
@ConfigurationPropertiesScan
@EnableScheduling
class HtmxDemoApplication {

    @Bean
    fun sessionRepository(): MapSessionRepository {
        return MapSessionRepository(ConcurrentHashMap())
    }
}

fun main(args: Array<String>) {
    runApplication<HtmxDemoApplication>(*args)
}
