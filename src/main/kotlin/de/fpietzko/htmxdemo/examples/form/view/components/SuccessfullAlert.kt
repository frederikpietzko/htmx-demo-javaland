package de.fpietzko.htmxdemo.examples.form.view.components

import de.fpietzko.htmxdemo.html.htmx.hyperscript
import de.fpietzko.htmxdemo.domain.visitors.Visitor
import kotlinx.html.FlowContent
import kotlinx.html.div
import kotlinx.html.span

fun FlowContent.successfullAlert(visitor: Visitor) {
    div(
        "alert relative alert-success"
    ) {
        hyperscript("on load wait 2s then add .hidden")
        span { +"Successfully created Visitor ${visitor.name}" }
    }
}