package de.fpietzko.javaland.examples.index

import de.fpietzko.javaland.components.layout.CommonLayout
import de.fpietzko.javaland.html.mvc.respondTemplate
import kotlinx.html.a
import kotlinx.html.div
import kotlinx.html.h1
import kotlinx.html.p
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseBody

@Controller
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