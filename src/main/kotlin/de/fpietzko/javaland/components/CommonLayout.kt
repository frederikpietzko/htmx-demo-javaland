package de.fpietzko.javaland.components

import de.fpietzko.javaland.html.templates.Slot
import de.fpietzko.javaland.html.templates.Template
import de.fpietzko.javaland.html.templates.insert
import kotlinx.html.FlowContent
import kotlinx.html.HTML

class CommonLayout(val title: String) : Template<HTML> {
    val children = Slot<FlowContent>()
    override fun HTML.render() {
        insert(BootstrapHtml(title)) {
            content {
                navbar {
                    menuItems = listOf(
                        Navbar.MenuItem(
                            "Form",
                            children = listOf(
                                Navbar.MenuItem("Basic Form", "/form"),
                                Navbar.MenuItem("Server Side Validation", "/form/server-validation"),
                            ),
                        ),
                        Navbar.MenuItem("Table", "/table"),
                        Navbar.MenuItem("Realtime", "/realtime"),
                    )
                }
                content {
                    insert(children)
                }
            }
        }
    }
}