package de.fpietzko.javaland.examples.form.view

import de.fpietzko.javaland.examples.form.view.components.registerForm
import de.fpietzko.javaland.components.CommonLayout
import de.fpietzko.javaland.html.htmx.hxExt
import de.fpietzko.javaland.html.templates.insert
import de.fpietzko.javaland.html.htmx.wsConnect
import kotlinx.html.HTML
import kotlinx.html.div
import kotlinx.html.id

fun HTML.registerPage() = insert(CommonLayout("Basic Form")) {
    children {
        div("toast") {
            id = "toast"
            hxExt("ws")
            wsConnect = "/form/ws"
        }
        registerForm()
    }
}

