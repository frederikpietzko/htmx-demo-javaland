package de.fpietzko.javaland.examples.form.controller

import de.fpietzko.javaland.examples.form.model.RegisterFormSubmission
import de.fpietzko.javaland.examples.form.view.components.serverValidatedForm
import de.fpietzko.javaland.examples.form.view.serverValidatedRegisterPage
import de.fpietzko.javaland.html.mvc.HTMLResponseProvider
import de.fpietzko.javaland.html.mvc.respondHtml
import de.fpietzko.javaland.html.mvc.respondHtmlSnippet
import de.fpietzko.javaland.domain.visitors.VisitorService
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseBody

@Controller
@ResponseBody
@RequestMapping("/form/server-validation")
class ServerValidatedFormController(
    private val visitorService: VisitorService,
) {

    @GetMapping
    fun validatedForm() = respondHtml {
        serverValidatedRegisterPage()
    }

    @PostMapping
    fun submitForm(model: RegisterFormSubmission): HTMLResponseProvider {
        val errors = visitorService.validateSubmission(model)
        if (errors.isNotEmpty()) {
            return respondHtmlSnippet {
                serverValidatedForm(model, errors)
            }
        }
        visitorService.addVisitor(model.toVisitor())
        return respondHtmlSnippet {
            serverValidatedForm()
        }
    }


    @PostMapping("/validate")
    fun validateForm(model: RegisterFormSubmission): HTMLResponseProvider {
        return respondHtmlSnippet {
            serverValidatedForm(model, visitorService.validateSubmission(model))
        }
    }


}