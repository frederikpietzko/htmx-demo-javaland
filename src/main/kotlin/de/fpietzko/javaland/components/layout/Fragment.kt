package de.fpietzko.javaland.components.layout

import kotlinx.html.FlowContent
import kotlinx.html.HTMLTag
import kotlinx.html.TagConsumer

class Fragment(consumer: TagConsumer<*>) : HTMLTag("", consumer, initialAttributes = emptyMap(), "", false, false),
    FlowContent