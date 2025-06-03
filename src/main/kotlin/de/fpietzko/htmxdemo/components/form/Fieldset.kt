package de.fpietzko.htmxdemo.components.form

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

typealias InputAttributeBuilder = INPUT.() -> Unit

fun FlowContent.fieldset(props: FieldsetProps, attributeBuilder: InputAttributeBuilder = {}) =
    fieldSet("fieldset w-full") {
        props.id?.let { this.id = it }
        legend { +props.label }
        val classes = "w-full input" + if (props.error != null) " input-error" else ""
        input(type = props.type, name = props.name, classes = classes) {
            props.placeholder?.let { this.placeholder = it }
            attributeBuilder()
        }
        if (props.error != null) p("fieldset-label text-red-500") {
            +props.error
        }
        else props.helperText?.let {
            p("fieldset-label") {
                +it
            }
        }
    }
