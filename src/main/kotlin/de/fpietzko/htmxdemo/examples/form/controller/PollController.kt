package de.fpietzko.htmxdemo.examples.form.controller

import de.fpietzko.htmxdemo.components.layout.BootstrapHtml
import de.fpietzko.htmxdemo.examples.form.model.RegisterFormSubmission
import de.fpietzko.htmxdemo.examples.form.view.components.registerForm
import de.fpietzko.htmxdemo.html.mvc.HTMLResponseProvider
import de.fpietzko.htmxdemo.html.mvc.getAttributeOrDefault
import de.fpietzko.htmxdemo.html.mvc.respondTemplate
import de.fpietzko.htmxdemo.domain.visitors.VisitorRepository
import jakarta.servlet.http.HttpSession
import kotlinx.html.FlowContent
import kotlinx.html.div
import kotlinx.html.h1
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseBody

@Controller
@RequestMapping("/poll")
class PollController(
    private val visitorRepository: VisitorRepository,
) {


    @GetMapping
    @ResponseBody
    fun form(session: HttpSession): HTMLResponseProvider {
        val didSubmit = session.getAttributeOrDefault("didSubmit", false)
        if (didSubmit)
            return respondTemplate(BootstrapHtml("Thank you for submitting!")) {
                content {
                    content {
                        div("flex flex-col gap-4 w-sm lg:w-lg m-auto") {
                            thankYouForSubmitting()
                        }
                    }
                }
            }

        return respondTemplate(BootstrapHtml("Poll")) {
            content {
                content {
                    registerForm("/poll")
                }
            }
        }
    }

    @PostMapping
    @ResponseBody
    fun enterInPoll(model: RegisterFormSubmission, session: HttpSession): HTMLResponseProvider {
        session.setAttribute("didSubmit", true)
        visitorRepository.add(model.toVisitor())
        return respondTemplate(BootstrapHtml("Thank you for submitting!")) {
            content {
                content {
                    div("flex flex-col gap-4 w-sm lg:w-lg m-auto") {
                        thankYouForSubmitting()
                    }
                }
            }
        }
    }


    private fun FlowContent.thankYouForSubmitting() {
        h1("text-2xl text-center font-bold text-primary") {
            +"Thank you for submitting!"
        }
    }
}