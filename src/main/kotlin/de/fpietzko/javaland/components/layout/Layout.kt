package de.fpietzko.javaland.components.layout

import de.fpietzko.javaland.components.layout.Navbar
import de.fpietzko.javaland.html.templates.Slot
import de.fpietzko.javaland.html.templates.Template
import de.fpietzko.javaland.html.templates.TemplateSlot
import de.fpietzko.javaland.html.templates.insert
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