package de.fpietzko.htmxdemo.html.mvc

import org.springframework.http.HttpInputMessage
import org.springframework.http.HttpOutputMessage
import org.springframework.http.MediaType
import org.springframework.http.converter.HttpMessageConverter
import org.springframework.stereotype.Component

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