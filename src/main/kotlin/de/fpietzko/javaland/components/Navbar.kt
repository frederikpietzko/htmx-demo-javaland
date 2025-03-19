package de.fpietzko.javaland.components

import de.fpietzko.javaland.util.Template
import kotlinx.html.*

class Navbar : Template<FlowContent> {
    data class MenuItem(
        val label: String,
        val href: String = "",
        val children: List<MenuItem> = emptyList(),
    )

    var menuItems = listOf<MenuItem>()

    private fun UL.burgerMenuItem(menuItem: MenuItem) {
        li {
            a(href = menuItem.href) { +menuItem.label }
            if (menuItem.children.isNotEmpty()) {
                ul("p-2") {
                    for (child in menuItem.children) {
                        burgerMenuItem(child)
                    }
                }
            }
        }
    }

    private fun UL.centerMenuItem(menuItem: MenuItem) {
        li {
            if (menuItem.children.isNotEmpty()) {
                details {
                    summary { +menuItem.label }
                    ul("p-2 w-52 z-10") {
                        for (child in menuItem.children) {
                            centerMenuItem(child)
                        }
                    }
                }
            } else {
                a(href = menuItem.href) { +menuItem.label }
            }
        }
    }

    override fun FlowContent.render() {
        div("navbar bg-base-100 shadow-sm") {
            div("navbar-start") {
                div("dropdown") {
                    div("btn btn-ghost lg:hidden") {
                        tabIndex = "0"
                        role = "button"
                        i("fa fa-solid fa-bars h-6 w-6 text-black dark:text-white")
                    }
                    ul("menu menu-sm dropdown-content bg-base-100 rounded-box z-1 mt-3 w-52 p-2 shadow") {
                        tabIndex = "0"
                        for (menuItem in menuItems) {
                            burgerMenuItem(menuItem)
                        }
                    }
                }
                a(classes = "btn btn-ghost text-xl") {
                    span("text-secondary") {
                        +"Kotlin HTML"
                    }
                    span {
                        +" + "
                    }
                    span("text-primary") {
                        +"HTMX Demo"
                    }
                }
            }
            div("navbar-center hidden lg:flex") {
                ul("menu menu-horizontal px-1") {
                    for (menuItem in menuItems) {
                        centerMenuItem(menuItem)
                    }
                }
            }

            div("navbar-end") {
                a(href = "https://iits.de", classes = "btn btn-ghost") {
                    target = "_blank"
                    span {
                        +"Check out iits"
                    }
                    img(src = "/logo-iits-2024-final-filled.svg", classes = "h-8 w-8 ml-2")
                }
            }
        }
    }
}