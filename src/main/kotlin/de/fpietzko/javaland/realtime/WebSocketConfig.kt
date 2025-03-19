package de.fpietzko.javaland.realtime

import org.springframework.context.annotation.Configuration
import org.springframework.web.socket.config.annotation.EnableWebSocket
import org.springframework.web.socket.config.annotation.WebSocketConfigurer
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry
import org.springframework.web.socket.server.support.HttpSessionHandshakeInterceptor

@Configuration
@EnableWebSocket
class WebSocketConfig(
    private val websocketSessionHolder: WebsocketSessionHolder
) : WebSocketConfigurer {
    override fun registerWebSocketHandlers(registry: WebSocketHandlerRegistry) {
        registry.addHandler(websocketSessionHolder, "/realtime/ws")
            .addInterceptors(HttpSessionHandshakeInterceptor())
    }
}