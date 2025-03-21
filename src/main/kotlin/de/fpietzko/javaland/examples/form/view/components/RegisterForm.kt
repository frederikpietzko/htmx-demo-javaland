package de.fpietzko.javaland.examples.form.view.components

import de.fpietzko.javaland.components.form.FieldsetProps
import de.fpietzko.javaland.components.form.checkbox
import de.fpietzko.javaland.components.form.fieldset
import de.fpietzko.javaland.html.htmx.hxPost
import de.fpietzko.javaland.html.htmx.hxSwap
import de.fpietzko.javaland.html.htmx.hxTarget
import de.fpietzko.javaland.html.htmx.hxTrigger
import kotlinx.html.*

fun FlowContent.registerForm(action: String = "/form") {
    form(classes = "flex flex-col gap-4 w-sm lg:w-lg m-auto") {
        id = "registerForm"
        hxTrigger = "submit"
        hxPost = action
        hxTarget = "#registerForm"
        hxSwap = "outerHTML"

        h4("text-2xl text-center font-bold text-primary") {
            +"Register as a Javaland Visitor"
        }
        fieldset(
            FieldsetProps(
                "Name",
                name = "name",
                placeholder = "Max Mustermann",
                helperText = "Please enter your full name"
            )
        ) {
            required = true
        }
        fieldset(FieldsetProps("Age", name = "age", type = InputType.number, helperText = "Please enter your age")) {
            required = true
            min = "18"
            max = "100"
        }
        div("grid grid-cols-2 gap-2") {
            checkbox("Knows Kotlin", "knowsKotlin")
            checkbox("Knows Java", "knowsJava")
            checkbox("Knows HTMX", "knowsHtmx")
            checkbox("Dislikes JavaScript", "dislikesJavascript")
        }
        button(classes = "btn btn-primary mt-4", type = ButtonType.submit) {
            +"Submit"
        }
    }
}
