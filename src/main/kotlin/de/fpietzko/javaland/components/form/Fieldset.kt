package de.fpietzko.javaland.components.form

import kotlinx.html.*

data class FieldsetProps(
    val label: String,
    val name: String,
    val type: InputType = InputType.text,
    val placeholder: String? = null,
    val helperText: String? = null,
    val error: String? = null,
    val id: String? = null,
)

fun FlowContent.fieldset(
    props: FieldsetProps,
    block: INPUT.() -> Unit = {}
) = fieldSet("fieldset w-full") {
    props.id?.let { this.id = it }
    legend { +props.label }
    input(
        type = props.type,
        name = name,
        classes = "w-full input " + if (props.error != null) "input-error" else "",
    ) {
        props.placeholder?.let { this.placeholder = it }
        block()
    }
    if (props.error != null) {
        p("fieldset-label text-red-500") {
            +props.error
        }
    } else {
        props.helperText?.let {
            p("fieldset-label") {
                +it
            }
        }
    }
}
