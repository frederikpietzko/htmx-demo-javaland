package de.fpietzko.javaland

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean
import org.springframework.session.MapSessionRepository
import org.springframework.session.config.annotation.web.http.EnableSpringHttpSession
import java.util.concurrent.ConcurrentHashMap

@SpringBootApplication
@EnableSpringHttpSession
class HtmxDemoJavalandApplication {

    @Bean
    fun sessionRepository(): MapSessionRepository {
        return MapSessionRepository(ConcurrentHashMap())
    }
}

fun main(args: Array<String>) {
    runApplication<HtmxDemoJavalandApplication>(*args)
}
