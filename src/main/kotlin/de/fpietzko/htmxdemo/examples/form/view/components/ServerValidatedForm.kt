package de.fpietzko.htmxdemo.examples.form.view.components

import de.fpietzko.htmxdemo.components.form.FieldsetProps
import de.fpietzko.htmxdemo.components.form.checkbox
import de.fpietzko.htmxdemo.components.form.fieldset
import de.fpietzko.htmxdemo.examples.form.model.RegisterFormSubmission
import de.fpietzko.htmxdemo.html.htmx.*
import de.fpietzko.htmxdemo.html.mvc.conferenceName
import kotlinx.html.*

fun FlowContent.serverValidatedForm(model: RegisterFormSubmission? = null, errors: Map<String, String> = emptyMap()) {
    form(classes = "flex flex-col gap-4 w-sm lg:w-lg m-auto") {
        id = "registerForm"
        hxTrigger = "submit"
        hxPost = "/form/server-validation"
        hxTarget = "#registerForm"


        h4("text-2xl text-center font-bold text-primary") {
            +"Register as a $conferenceName Visitor"
        }
        fieldset(
            FieldsetProps(
                "Name",
                id = "name",
                name = "name",
                placeholder = "Max Mustermann",
                helperText = "Please enter your full name",
                error = errors["name"]
            )
        ) {
            hxTrigger = "change"
            hxPost = "/form/server-validation/validate"
            hxTarget = "#name"
            hxSwap = "outerHTML"
            hxSelect = "#name"
            model?.name?.let { value = it }
        }
        fieldset(
            FieldsetProps(
                "Age",
                id = "age",
                name = "age",
                type = InputType.number,
                helperText = "Please enter your age",
                error = errors["age"]
            )
        ) {
            hxTrigger = "change"
            hxPost = "/form/server-validation/validate"
            hxTarget = "#age"
            hxSelect = "#age"
            hxSwap = "outerHTML"
            model?.age?.let { value = it.toString() }
        }
        div("grid grid-cols-2 gap-2") {
            checkbox("Knows Kotlin", "knowsKotlin") {
                model?.knowsKotlin?.let { checked = it }
            }
            checkbox("Knows Java", "knowsJava") {
                model?.knowsJava?.let { checked = it }
            }
            checkbox("Knows HTMX", "knowsHtmx") {
                model?.knowsHtmx?.let { checked = it }
            }
            checkbox("Dislikes JavaScript", "dislikesJavascript") {
                model?.dislikesJavascript?.let { checked = it }
            }
        }
        button(classes = "btn btn-primary mt-4", type = ButtonType.submit) {
            +"Submit"
        }
    }
}
