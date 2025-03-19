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
                        svg("w-5 h-5") {
                            attributes["fill"] = "none"
                            attributes["viewBox"] = "0 0 24 24"
                            attributes["stroke"] = "currentColor"
                            +"""<path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M4 6h16M4 12h8m-8 6h16" />"""
                        }
                    }
                    ul("menu menu-sm dropdown-content bg-base-100 rounded-box z-1 mt-3 w-52 p-2 shadow") {
                        tabIndex = "0"
                        for (menuItem in menuItems) {
                            burgerMenuItem(menuItem)
                        }
                    }
                }
                a(classes = "btn btn-ghost text-xl") { +"Kotlin HTMX+Kotlin Demo" }
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