package de.fpietzko.javaland

import de.fpietzko.javaland.shared.CommonLayout
import de.fpietzko.javaland.util.HtmlController
import de.fpietzko.javaland.util.respondTemplate
import kotlinx.html.a
import kotlinx.html.div
import kotlinx.html.h1
import kotlinx.html.p
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseBody

@HtmlController
@RequestMapping("/")
class IndexController {
    @GetMapping
    @ResponseBody
    fun index() = respondTemplate(CommonLayout("Javaland HTMX Demo")) {
        children {
            div("hero bg-base-200 m-auto") {
                div("hero-content text-center") {
                    div("max-w-lg") {
                        h1("text-5xl font-bold") {
                            +" Hello Javaland 2025!"
                        }
                        p("p-6") {
                            +"This is a demo showing the power of kotlinx.html and HTMX!"
                        }
                        a(href = "/form", classes = "btn btn-primary") {
                            +"Get started"
                        }
                    }
                }
            }
        }
    }
}
