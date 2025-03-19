package de.fpietzko.javaland.components

import de.fpietzko.javaland.util.*
import kotlinx.html.*

class BootstrapHtml(
    val title: String,
) : Template<HTML> {
    val content = TemplateSlot<Layout>()

    override fun HTML.render() {
        head {
            title(this@BootstrapHtml.title)
            meta {
                name = "viewport"
                content = "width=device-width, initial-scale=1.0"
            }
            script {
                src = "/htmx.min.js"
                defer = true
            }
            script {
                src = "/htmx.ext.sse.min.js"
                defer = true
            }

            script {
                src = "/htmx.ext.ws.min.js"
                defer = true
            }
            script {
                src = "/hyperscript.min.js"
                defer = true
            }


            link(
                href = "/fa-all.min.css",
                rel = "stylesheet",
            )
            link(href = "/daisyui.min.css", rel = "stylesheet")
            script { src = "/tailwind.min.js" }
        }
        body("bg-base-200 min-h-screen min-w-screen flex flex-col") {
            hxBoost()
            hxExt("sse ws")
            insert(Layout(), content)
        }
    }
}