package de.fpietzko.javaland.examples.table.view

import de.fpietzko.javaland.components.layout.CommonLayout
import de.fpietzko.javaland.examples.table.model.TableModel
import de.fpietzko.javaland.examples.table.view.components.visitorSearchAndAddBar
import de.fpietzko.javaland.examples.table.view.components.visitorTable
import de.fpietzko.javaland.html.htmx.*
import de.fpietzko.javaland.html.templates.insert
import kotlinx.html.*

fun HTML.visitorTablePage(model: TableModel) = insert(CommonLayout("Table Demo")) {
    children {
        div("toast") {
            id = "toast"
            hxExt("ws")
            wsConnect = "/form/ws"
        }
        div("flex flex-col p-4 gap-3") {
            visitorSearchAndAddBar()
            div {
                hxTrigger = "load"
                hxGet = "/table/drawer"
                hxSwap = "outerHTML"
            }
            visitorTable(model)
        }
    }
}