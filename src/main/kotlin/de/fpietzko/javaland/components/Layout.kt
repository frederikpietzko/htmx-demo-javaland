package de.fpietzko.javaland.components

import de.fpietzko.javaland.util.*
import kotlinx.html.*

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

