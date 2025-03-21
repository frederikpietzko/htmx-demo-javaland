package de.fpietzko.javaland.form

import de.fpietzko.javaland.components.checkbox
import de.fpietzko.javaland.components.fieldset
import de.fpietzko.javaland.form.model.RegisterFormSubmission
import de.fpietzko.javaland.shared.CommonLayout
import de.fpietzko.javaland.util.*
import de.fpietzko.javaland.visitors.JavalandVisitorRepository
import kotlinx.html.*
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter
import java.util.concurrent.CopyOnWriteArrayList

@HtmlController
@RequestMapping("/form")
class FormController(
    private val visitorRepository: JavalandVisitorRepository,
) {
    private val emitters: MutableList<SseEmitter> = CopyOnWriteArrayList()

    @GetMapping
    @ResponseBody
    fun basicForm() = respondTemplate(CommonLayout("Basic Form")) {
        children {
            div("toast") {
                id = "toast"
                hxExt("sse")
                sseSwap = "message"
                sseConnect = "/form/success-sse"
            }
            registerForm()
        }
    }

    @PostMapping
    @ResponseBody
    fun submitForm(model: RegisterFormSubmission): HTMLResponseProvider {
        visitorRepository.add(model.toVisitor())
        sendSse("Added Visitor ${model.name}!")
        return respondHtmlSnippet {
            registerForm()
        }
    }

    @GetMapping("/success-sse")
    fun handleSSE(): SseEmitter {
        val emitter = SseEmitter()
        emitters.add(emitter)
        emitter.onCompletion {
            emitters.remove(emitter)
        }
        emitter.onTimeout {
            emitters.remove(emitter)
        }
        emitter.onError {
            emitters.remove(emitter)
        }
        return emitter
    }

    private enum class SubmissionResultType {
        SUCCESS, FAILURE
    }

    private fun sendSse(message: String, type: SubmissionResultType = SubmissionResultType.SUCCESS) {
        emitters.forEach { emitter ->
            emitter.runCatching {
                sendHtml {
                    div(
                        "alert relative " + when (type) {
                            SubmissionResultType.SUCCESS -> "alert-success"
                            SubmissionResultType.FAILURE -> "alert-error"
                        }
                    ) {
                        hyperscript("on load wait 2s then add .hidden")
                        span { +message }
                    }
                }
            }.onFailure {
                emitters.remove(emitter)
            }
        }
    }
}

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
            "Name",
            name = "name",
            placeholder = "Max Mustermann",
            helperText = "Please enter your full name"
        ) {
            required = true
        }
        fieldset("Age", name = "age", type = InputType.number, helperText = "Please enter your age") {
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

