package de.fpietzko.htmxdemo.examples.form.view

import de.fpietzko.htmxdemo.examples.form.view.components.registerForm
import de.fpietzko.htmxdemo.components.layout.CommonLayout
import de.fpietzko.htmxdemo.html.htmx.hxExt
import de.fpietzko.htmxdemo.html.templates.insert
import de.fpietzko.htmxdemo.html.htmx.wsConnect
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

