package de.fpietzko.htmxdemo.examples.index

import de.fpietzko.htmxdemo.components.layout.CommonLayout
import de.fpietzko.htmxdemo.html.mvc.conferenceName
import de.fpietzko.htmxdemo.html.mvc.conferenceYear
import de.fpietzko.htmxdemo.html.mvc.respondTemplate
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
    fun index() = respondTemplate(CommonLayout("$conferenceName HTMX Demo")) {
        children {
            div("hero bg-base-200 m-auto") {
                div("hero-content text-center") {
                    div("max-w-lg") {
                        h1("text-5xl font-bold") {
                            +" Hello $conferenceName $conferenceYear!"
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