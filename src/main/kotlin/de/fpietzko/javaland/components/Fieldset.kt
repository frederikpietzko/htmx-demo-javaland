package de.fpietzko.javaland.components

import kotlinx.html.*


fun FlowContent.fieldset(
    label: String,
    name: String,
    type: InputType = InputType.text,
    placeholder: String? = null,
    helperText: String? = null,
    error: String? = null,
    id: String? = null,
    block: INPUT.() -> Unit = {}
) = fieldSet("fieldset w-full") {
    id?.let { this.id = it }
    legend { +label }
    input(
        type = type,
        name = name,
        classes = "w-full input " + if (error != null) "input-error" else "",
    ) {
        placeholder?.let { this.placeholder = it }
        block()
    }
    if (error != null) {
        p("fieldset-label text-red-500") {
            +error
        }
    } else {
        helperText?.let {
            p("fieldset-label") {
                +it
            }
        }
    }
}

fun FlowContent.checkbox(label: String, name: String, block: INPUT.() -> Unit = {}) =
    fieldSet("flex gap-1") {
        input(type = InputType.checkBox, name = name, classes = "checkbox") {
            block()
        }
        label("text-sm") {
            +label
        }
    }