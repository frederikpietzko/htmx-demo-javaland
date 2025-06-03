package de.fpietzko.htmxdemo.components.layout

import de.fpietzko.htmxdemo.html.templates.Slot
import de.fpietzko.htmxdemo.html.templates.Template
import de.fpietzko.htmxdemo.html.templates.TemplateSlot
import de.fpietzko.htmxdemo.html.templates.insert
import kotlinx.html.BODY
import kotlinx.html.FlowContent

class Layout : Template<BODY> {
    val navbar = TemplateSlot<Navbar>()
    val content = Slot<FlowContent>()

    override fun BODY.render() {
        if (!navbar.isEmpty) {
            insert(Navbar(), navbar)
        }
        insert(content)
    }
}