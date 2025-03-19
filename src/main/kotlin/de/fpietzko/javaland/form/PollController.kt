package de.fpietzko.javaland.form

import de.fpietzko.javaland.components.BootstrapHtml
import de.fpietzko.javaland.form.model.RegisterFormSubmission
import de.fpietzko.javaland.util.HTMLResponseProvider
import de.fpietzko.javaland.util.HtmlController
import de.fpietzko.javaland.util.getAttributeOrDefault
import de.fpietzko.javaland.util.respondTemplate
import de.fpietzko.javaland.visitors.JavalandVisitorRepository
import jakarta.servlet.http.HttpSession
import kotlinx.html.FlowContent
import kotlinx.html.div
import kotlinx.html.h1
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseBody

@HtmlController
@RequestMapping("/poll")
class PollController(
    private val visitorRepository: JavalandVisitorRepository,
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