package de.fpietzko.javaland.examples.table.view.components

import de.fpietzko.javaland.examples.form.view.components.registerForm
import kotlinx.html.FlowContent
import kotlinx.html.InputType
import kotlinx.html.div
import kotlinx.html.id
import kotlinx.html.input
import kotlinx.html.label

fun FlowContent.addVisitorDrawer() = div("drawer drawer-end") {
    input(classes = "drawer-toggle", type = InputType.checkBox) {
        id = "drawer-toggle-1"
    }
    div("drawer-side z-100") {
        label("drawer-overlay") {
            htmlFor = "drawer-toggle-1"
            attributes["aria-label"] = "close sidebar"
        }
        div("z-10 bg-base-100 p-4 min-h-full text-base-content w-[800px]") {
            registerForm("/table/add")
        }
    }
}