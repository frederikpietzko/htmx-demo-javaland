package de.fpietzko.javaland.components.form

import kotlinx.html.FlowContent
import kotlinx.html.INPUT
import kotlinx.html.InputType
import kotlinx.html.fieldSet
import kotlinx.html.input
import kotlinx.html.label


fun FlowContent.checkbox(label: String, name: String, block: INPUT.() -> Unit = {}) =
    fieldSet("flex gap-1") {
        input(type = InputType.checkBox, name = name, classes = "checkbox") {
            block()
        }
        label("text-sm") {
            +label
        }
    }
