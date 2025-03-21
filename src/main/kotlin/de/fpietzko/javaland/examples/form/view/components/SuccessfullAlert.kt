package de.fpietzko.javaland.examples.form.view.components

import de.fpietzko.javaland.html.htmx.hyperscript
import de.fpietzko.javaland.domain.visitors.JavalandVisitor
import kotlinx.html.FlowContent
import kotlinx.html.div
import kotlinx.html.span

fun FlowContent.successfullAlert(visitor: JavalandVisitor) {
    div(
        "alert relative alert-success"
    ) {
        hyperscript("on load wait 2s then add .hidden")
        span { +"Successfully created Visitor ${visitor.name}" }
    }
}