package de.fpietzko.javaland.config.websocket

import de.fpietzko.javaland.html.mvc.respondHtmlSnippet
import kotlinx.html.FlowContent
import org.springframework.stereotype.Component
import org.springframework.web.socket.CloseStatus
import org.springframework.web.socket.TextMessage
import org.springframework.web.socket.WebSocketSession
import org.springframework.web.socket.handler.AbstractWebSocketHandler
import java.util.concurrent.ConcurrentHashMap

@Component
class WebsocketSessionHolder : AbstractWebSocketHandler() {
    val sessions = ConcurrentHashMap<String, WebSocketSession>()

    override fun afterConnectionEstablished(session: WebSocketSession) {
        super.afterConnectionEstablished(session)
        sessions[session.id] = session
    }

    override fun afterConnectionClosed(session: WebSocketSession, status: CloseStatus) {
        super.afterConnectionClosed(session, status)
        sessions.remove(session.id)
    }

    fun broadcastHtmlSnippet(block: FlowContent.() -> Unit) {
        val html = respondHtmlSnippet(block).html()
        sessions.values.forEach { session ->
            session.sendMessage(TextMessage(html))
        }
    }

    fun sendHtmlSnippet(sessionId: String, block: FlowContent.() -> Unit) {
        val html = respondHtmlSnippet(block).html()
        sessions[sessionId]?.sendMessage(TextMessage(html))
    }
}