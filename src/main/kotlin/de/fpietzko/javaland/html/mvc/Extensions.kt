package de.fpietzko.javaland.html.mvc

import de.fpietzko.javaland.html.templates.Template
import jakarta.servlet.http.HttpSession
import kotlinx.html.*
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter

fun respondHtml(block: HTML.() -> Unit): HTMLResponseProvider {
    return HTMLResponse(block)
}

fun respondHtmlSnippet(block: FlowContent.() -> Unit): HTMLResponseProvider {
    return HTMLSnippetResponse(block)
}

fun SseEmitter.sendHtml(block: FlowContent.() -> Unit) {
    send(respondHtmlSnippet(block).html())
}

fun <TTemplate : Template<HTML>> respondTemplate(template: TTemplate, body: TTemplate.() -> Unit) = respondHtml {
    template.body()
    with(template) {
        render()
    }
}

inline fun <reified T> HttpSession.getAttributeOrDefault(name: String, default: T): T {
    return getAttribute(name) as? T ?: default
}
