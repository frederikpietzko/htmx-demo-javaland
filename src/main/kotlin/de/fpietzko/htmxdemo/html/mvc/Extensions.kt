package de.fpietzko.htmxdemo.html.mvc

import de.fpietzko.htmxdemo.config.websocket.ConferenceConfig
import de.fpietzko.htmxdemo.html.templates.Template
import jakarta.servlet.http.HttpSession
import kotlinx.html.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.ApplicationContext
import org.springframework.context.ApplicationContextAware
import org.springframework.stereotype.Component
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

@Component
private class ApplicationContextProvider : ApplicationContextAware {

    override fun setApplicationContext(applicationContext: ApplicationContext) {
        Companion.applicationContext = applicationContext
    }

    companion object {
        var applicationContext: ApplicationContext? = null
            private set
    }


}

val ApplicationContext by lazy { ApplicationContextProvider.applicationContext!! }
val conferenceConfig by lazy { ApplicationContext.getBean(ConferenceConfig::class.java) }
val conferenceName by lazy { conferenceConfig.conferenceName }
val conferenceYear by lazy { conferenceConfig.conferenceYear }