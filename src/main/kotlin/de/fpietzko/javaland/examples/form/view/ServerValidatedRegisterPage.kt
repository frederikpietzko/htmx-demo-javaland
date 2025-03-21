package de.fpietzko.javaland.examples.form.view

import de.fpietzko.javaland.examples.form.view.components.serverValidatedForm
import de.fpietzko.javaland.html.templates.insert
import de.fpietzko.javaland.components.CommonLayout
import kotlinx.html.HTML

fun HTML.serverValidatedRegisterPage() {
    insert(CommonLayout("Server Validated Form")) {
        children {
            serverValidatedForm()
        }
    }
}