package de.fpietzko.htmxdemo.examples.table.view

import de.fpietzko.htmxdemo.components.layout.CommonLayout
import de.fpietzko.htmxdemo.examples.table.model.TableModel
import de.fpietzko.htmxdemo.examples.table.view.components.visitorSearchAndAddBar
import de.fpietzko.htmxdemo.examples.table.view.components.visitorTable
import de.fpietzko.htmxdemo.html.htmx.*
import de.fpietzko.htmxdemo.html.templates.insert
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