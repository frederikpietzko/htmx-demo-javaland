package de.fpietzko.javaland.html.mvc

import kotlinx.html.HTML
import kotlinx.html.html
import kotlinx.html.stream.appendHTML

class HTMLResponse(val block: HTML.() -> Unit) : HTMLResponseProvider {
    override fun html(): String {
        return buildString {
            append("<!DOCTYPE html>")
            appendHTML().html(block = block)
        }
    }
}