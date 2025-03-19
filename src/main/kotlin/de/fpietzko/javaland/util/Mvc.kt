package de.fpietzko.javaland.util

import kotlinx.html.*
import kotlinx.html.consumers.delayed
import kotlinx.html.stream.HTMLStreamBuilder
import kotlinx.html.stream.appendHTML
import org.springframework.http.HttpInputMessage
import org.springframework.http.HttpOutputMessage
import org.springframework.http.MediaType
import org.springframework.http.converter.HttpMessageConverter
import org.springframework.stereotype.Component
import org.springframework.stereotype.Controller
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter

@Controller
annotation class HtmlController

interface HTMLResponseProvider {
    fun html(): String
}

class HTMLResponse(val block: HTML.() -> Unit) : HTMLResponseProvider {
    override fun html(): String {
        return buildString {
            append("<!DOCTYPE html>")
            appendHTML().html(block = block)
        }
    }
}

class Fragment(consumer: TagConsumer<*>) : HTMLTag("", consumer, initialAttributes = emptyMap(), "", false, false),
    FlowContent

class HTMLSnippetResponse(val block: FlowContent.() -> Unit) : HTMLResponseProvider {
    override fun html(): String {
        return buildString {
            HTMLStreamBuilder(this, false, false)
                .delayed()
                .let { Fragment(it).block() }
        }
    }
}

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

@Component
class HtmlMessageConverter : HttpMessageConverter<HTMLResponseProvider> {
    override fun canRead(clazz: Class<*>, mediaType: MediaType?): Boolean {
        return false
    }

    override fun canWrite(clazz: Class<*>, mediaType: MediaType?): Boolean {
        return HTMLResponseProvider::class.java.isAssignableFrom(clazz)
    }

    override fun getSupportedMediaTypes(): List<MediaType?> {
        return listOf(MediaType.TEXT_HTML)
    }

    override fun read(
        clazz: Class<out HTMLResponseProvider?>,
        inputMessage: HttpInputMessage,
    ): HTMLResponse {
        throw UnsupportedOperationException("Not supported")
    }

    override fun write(
        t: HTMLResponseProvider,
        contentType: MediaType?,
        outputMessage: HttpOutputMessage,
    ) {
        outputMessage.headers.contentType = contentType ?: MediaType.TEXT_HTML
        outputMessage.body.write(t.html().toByteArray())
    }

}

