package de.fpietzko.htmxdemo.examples.form.view

import de.fpietzko.htmxdemo.examples.form.view.components.serverValidatedForm
import de.fpietzko.htmxdemo.html.templates.insert
import de.fpietzko.htmxdemo.components.layout.CommonLayout
import kotlinx.html.HTML

fun HTML.serverValidatedRegisterPage() {
    insert(CommonLayout("Server Validated Form")) {
        children {
            serverValidatedForm()
        }
    }
}