package de.fpietzko.htmxdemo.examples.table.view.components

import de.fpietzko.htmxdemo.html.htmx.hxGet
import de.fpietzko.htmxdemo.html.htmx.hxSwap
import de.fpietzko.htmxdemo.html.htmx.hxTarget
import de.fpietzko.htmxdemo.html.htmx.hxTrigger
import kotlinx.html.FlowContent
import kotlinx.html.InputType
import kotlinx.html.div
import kotlinx.html.i
import kotlinx.html.id
import kotlinx.html.input
import kotlinx.html.label

fun FlowContent.visitorSearchAndAddBar() {
    div("flex gap-2") {
        label("input w-full flex items-center") {
            i("h-[1em] opacity-50 fa fa-solid fa-search")
            input(InputType.search, classes = "grow") {
                id = "search"
                placeholder = "Search"
                name = "search"
                hxTrigger = "keyup changed delay:500ms"
                hxGet = "/table/searchTable"
                hxTarget = "#visitorTable"
                hxSwap = "outerHTML"
            }
        }

        label("drawer-button btn btn-primary") {
            htmlFor = "drawer-toggle-1"
            +"Add"
        }
    }
}