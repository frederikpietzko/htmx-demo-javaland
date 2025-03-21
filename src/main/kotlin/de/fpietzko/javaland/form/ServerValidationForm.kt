package de.fpietzko.javaland.form


import de.fpietzko.javaland.components.BootstrapHtml
import de.fpietzko.javaland.components.checkbox
import de.fpietzko.javaland.components.fieldset
import de.fpietzko.javaland.form.model.RegisterFormSubmission
import de.fpietzko.javaland.shared.CommonLayout
import de.fpietzko.javaland.util.*
import de.fpietzko.javaland.visitors.JavalandVisitorRepository
import jakarta.servlet.http.HttpSession
import kotlinx.html.*
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter
import java.util.concurrent.CopyOnWriteArrayList


@HtmlController
@RequestMapping("/form/server-validation")
class ServerValidationForm(
    private val visitorRepository: JavalandVisitorRepository,
) {

    @GetMapping
    @ResponseBody
    fun validatedForm() = respondTemplate(CommonLayout("Validated Form")) {
        children {
            serverValidatedForm()
        }
    }

    private fun validate(model: RegisterFormSubmission): Map<String, String> {
        val errors = mutableMapOf<String, String>()
        if (model.name.isBlank()) {
            errors["name"] = "Name is required"
        }
        if (model.name.length < 3) {
            errors["name"] = "Name must be at least 3 characters long"
        }
        if (model.age.isBlank()) {
            errors["age"] = "Age is required"
        }
        when (model.age.toIntOrNull()) {
            null -> errors["age"] = "Age must be a number"
            in Int.MIN_VALUE..17 -> errors["age"] = "You must be at least 18 years old"
            in 100..Int.MAX_VALUE -> errors["age"] = "You must be younger than 100 years"
        }
        if (!model.knowsKotlin && !model.knowsJava && !model.knowsHtmx) {
            errors["knowsKotlin"] = "You must know at least one programming language"
        }
        if (!model.dislikesJavascript) {
            errors["dislikesJavascript"] = "You must dislike JavaScript"
        }
        return errors
    }

    @PostMapping("/validate")
    @ResponseBody
    fun validateForm(model: RegisterFormSubmission): HTMLResponseProvider {
        return respondHtmlSnippet {
            serverValidatedForm(model, validate(model))
        }
    }

    @PostMapping
    @ResponseBody
    fun submitForm(model: RegisterFormSubmission): HTMLResponseProvider {
        val errors = validate(model)
        if (errors.isEmpty()) {
            return respondHtmlSnippet {
                serverValidatedForm(model, errors)
            }
        }
        visitorRepository.add(model.toVisitor())
        return respondHtmlSnippet {
            serverValidatedForm()
        }
    }
}

fun FlowContent.serverValidatedForm(model: RegisterFormSubmission? = null, errors: Map<String, String> = emptyMap()) {
    form(classes = "flex flex-col gap-4 w-sm lg:w-lg m-auto") {
        id = "registerForm"
        hxTrigger = "submit"
        hxPost = "/form"
        hxTarget = "#registerForm"

        h4("text-2xl text-center font-bold text-primary") {
            +"Register as a Javaland Visitor"
        }
        fieldset(
            "Name",
            id = "name",
            name = "name",
            placeholder = "Max Mustermann",
            helperText = "Please enter your full name",
            error = errors["name"]
        ) {
            hxTrigger = "change"
            hxPost = "/form/server-validation/validate"
            hxTarget = "#name"
            hxSwap = "outerHTML"
            hxSelect = "#name"
            model?.name?.let { value = it }
        }
        fieldset(
            "Age",
            id = "age",
            name = "age",
            type = InputType.number,
            helperText = "Please enter your age",
            error = errors["age"]
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
