package de.fpietzko.htmxdemo.html.mvc

import de.fpietzko.htmxdemo.components.layout.Fragment
import kotlinx.html.FlowContent
import kotlinx.html.consumers.delayed
import kotlinx.html.stream.HTMLStreamBuilder

class HTMLSnippetResponse(val block: FlowContent.() -> Unit) : HTMLResponseProvider {
    override fun html(): String {
        return buildString {
            HTMLStreamBuilder(this, false, false)
                .delayed()
                .let { Fragment(it).block() }
        }
    }
}