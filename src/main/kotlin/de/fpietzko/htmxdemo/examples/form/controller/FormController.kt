package de.fpietzko.htmxdemo.examples.form.controller

import de.fpietzko.htmxdemo.config.websocket.WebsocketSessionHolder
import de.fpietzko.htmxdemo.examples.form.model.RegisterFormSubmission
import de.fpietzko.htmxdemo.examples.form.view.components.registerForm
import de.fpietzko.htmxdemo.examples.form.view.components.successfullAlert
import de.fpietzko.htmxdemo.examples.form.view.registerPage
import de.fpietzko.htmxdemo.html.mvc.HTMLResponseProvider
import de.fpietzko.htmxdemo.html.mvc.respondHtml
import de.fpietzko.htmxdemo.html.mvc.respondHtmlSnippet
import de.fpietzko.htmxdemo.domain.visitors.VisitorService
import jakarta.servlet.http.HttpSession
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseBody

@Controller
@ResponseBody
@RequestMapping("/form")
class FormController(
    private val websocketSessionHolder: WebsocketSessionHolder,
    private val visitorService: VisitorService,
) {

    @GetMapping
    fun basicForm() = respondHtml { registerPage() }

    @PostMapping
    fun submitForm(session: HttpSession, model: RegisterFormSubmission): HTMLResponseProvider {
        val visitor = visitorService.addVisitor(model.toVisitor())

        websocketSessionHolder.sendHtmlSnippet(session.id) {
            successfullAlert(visitor)
        }

        return respondHtmlSnippet {
            registerForm()
        }
    }
}



