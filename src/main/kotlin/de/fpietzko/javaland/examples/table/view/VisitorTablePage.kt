package de.fpietzko.javaland.examples.table.view

import de.fpietzko.javaland.examples.form.view.components.registerForm
import de.fpietzko.javaland.examples.table.model.TableModel
import de.fpietzko.javaland.html.htmx.hxExt
import de.fpietzko.javaland.html.htmx.wsConnect
import de.fpietzko.javaland.html.templates.insert
import de.fpietzko.javaland.components.CommonLayout
import de.fpietzko.javaland.examples.table.view.components.visitorTable
import kotlinx.html.HTML
import kotlinx.html.div
import kotlinx.html.id

fun HTML.visitorTablePage(model: TableModel) = insert(CommonLayout("Table Demo")) {
    children {
        div("toast") {
            id = "toast"
            hxExt("ws")
            wsConnect = "/form/ws"
        }
        registerForm()
        visitorTable(model)
    }
}